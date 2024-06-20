package com.backend.questify.Service;

import com.backend.questify.DTO.SubmissionDto;
import com.backend.questify.Entity.*;
import com.backend.questify.Exception.BadRequestException;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Model.ExecutionResponse;
import com.backend.questify.Repository.*;
import com.backend.questify.Util.DtoMapper;
import com.backend.questify.Util.EntityHelper;
import com.github.codeboy.piston4j.api.*;
import com.github.codeboy.piston4j.api.Runtime;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.backend.questify.Model.Role.ProfAcc;
import static com.backend.questify.Model.Role.StdAcc;

@Service
public class SubmissionService {
	@Autowired
	private SubmissionRepository submissionRepository;

	@Autowired
	private LaboratoryRepository laboratoryRepository;

	@Autowired
	private EntityHelper entityHelper;


	//! Todo : Add check if student can do anything ( occupied classroom )

	@Transactional
	public SubmissionDto updateSubmissionContent(UUID laboratoryId, String language, String updatedCode) {
		Submission submission = getCurrentSubmission(laboratoryId);

		Map<String, String> snippets = submission.getCodeSnippets();
		snippets.put(language, updatedCode);
		submissionRepository.save(submission);

		return DtoMapper.INSTANCE.submissionToSubmissionDto(submission);
	}


	public Submission saveSubmission(Submission submission) {
		return submissionRepository.save(submission);
	}


	public SubmissionDto getAndCreateSubmission(UUID laboratoryId) throws Exception {
		Long userId = entityHelper.getCurrentUserId();
		User user = entityHelper.getCurrentUser();

		Optional<Laboratory> laboratoryResult = laboratoryRepository.findById(laboratoryId);
		Laboratory laboratory = laboratoryResult.orElseThrow(
				() -> new ResourceNotFoundException("Laboratory Not Found With This Id: " + laboratoryId));

		Submission submission = null;

		if (user.getRole() == StdAcc) {

			Student student = entityHelper.findStudentById(userId);

			Optional<Submission> existingSubmission = submissionRepository.findByLaboratoryAndStudent(laboratory, student);
			if (existingSubmission.isPresent()) {
				return DtoMapper.INSTANCE.submissionToSubmissionDto(existingSubmission.get());
			}

			Submission newSubmission = Submission.builder()
												 .student(student)
												 .laboratory(laboratory)
												 .build();

			submission = submissionRepository.save(newSubmission);
		} else if (user.getRole() == ProfAcc) {

			Professor professor = entityHelper.findProfessorById(userId);

			Optional<Submission> existingSubmission = submissionRepository.findByLaboratoryAndProfessor(laboratory, professor);
			if (existingSubmission.isPresent()) {
				return DtoMapper.INSTANCE.submissionToSubmissionDto(existingSubmission.get());
			}

			Submission newSubmission = Submission.builder()
												 .professor(professor)
												 .laboratory(laboratory)
												 .build();

			submission = submissionRepository.save(newSubmission);
		} else {
			throw new Exception("Something Bad Happens To Submission"); //! Todo : Fix This
		}




		return DtoMapper.INSTANCE.submissionToSubmissionDto(submission);
	}

	public void deleteSubmission(Long id) {
		submissionRepository.deleteById(id);
	}

	public SubmissionDto resetSubmission(UUID laboratoryId) {
		Submission submission = getCurrentSubmission(laboratoryId);
		return DtoMapper.INSTANCE.submissionToSubmissionDto(submissionRepository.save(submission));
	}

	public ExecutionResponse executeSubmission(UUID laboratoryId, String language, UUID testCaseId) {

		SubmissionDto submissionDto = DtoMapper.INSTANCE.submissionToSubmissionDto(getCurrentSubmission(laboratoryId));

		ExecutionResult result = getExecutionResult(language, submissionDto, entityHelper.findTestCaseById(testCaseId).getInput());

		ExecutionResponse executionResponse = ExecutionResponse.builder()
															   .StdErr(result.getOutput()
																			 .getStderr())
															   .StdOut(result.getOutput()
																			 .getStdout())
															   .Output(result.getOutput()
																			 .getOutput())
															   .Code(result.getOutput()
																		   .getCode())
															   .Signal(result.getOutput()
																			 .getSignal())
															   .Language(result.getLanguage())
															   .Version(result.getVersion())
															   .build();

		return executionResponse;


	}

	private Submission getCurrentSubmission(UUID laboratoryId) {
		Long userId = entityHelper.getCurrentUserId();

		User user = entityHelper.getCurrentUser();

		Laboratory laboratory = entityHelper.findLaboratoryById(laboratoryId);

		Submission submission = null;

		if (user.getRole() == StdAcc) {
			submission = submissionRepository.findByLaboratoryAndStudent(laboratory, entityHelper.findStudentById(userId)).orElseThrow(
					() -> new ResourceNotFoundException("Submission Not Found With Laboratory Id : " + laboratoryId));

		} else if (user.getRole() == ProfAcc) {
			submission = submissionRepository.findByLaboratoryAndProfessor(laboratory, entityHelper.findProfessorById(userId)).orElseThrow(
					() -> new ResourceNotFoundException("Submission Not Found With Laboratory Id : " + laboratoryId));
		}

		return submission;
	}

	//! Todo : Inspect where to show error from compiling empty code

	private static ExecutionResult getExecutionResult(String language, SubmissionDto submissionDto, String stdin) {
		try {
			Map<String, String> snippets = submissionDto.getCodeSnippets();
			if (!snippets.containsKey(language)) {
				throw new ResourceNotFoundException("Submission For Language : " + language + " Is Not Available");
			}

			Piston api = Piston.getDefaultApi();

			String code = snippets.get(language);
			Optional<Runtime> optionalRuntime = api.getRuntime(language);

			Runtime runtime = optionalRuntime.orElseThrow(
					() -> new ResourceNotFoundException("Runtime Not Found With Language : " + language));



			CodeFile codeFile = new CodeFile("questify-coding-space", code);

//			ExecutionRequest
			ExecutionRequest request = new ExecutionRequest(runtime.getLanguage(), runtime.getVersion(), codeFile).setStdin(stdin);
//			request.setStdin("Hello");

			return api.execute(request);
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}catch (Exception e) {
			throw new BadRequestException("Unexpected Error With Compiler: " + e.getMessage());
		}

	}

}
