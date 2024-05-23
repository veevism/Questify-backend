package com.backend.questify.Service;

import com.backend.questify.DTO.SubmissionDto;
import com.backend.questify.Entity.Laboratory;
import com.backend.questify.Entity.Student;
import com.backend.questify.Entity.Submission;
import com.backend.questify.Exception.BadRequestException;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Model.ExecutionResponse;
import com.backend.questify.Repository.LaboratoryRepository;
import com.backend.questify.Repository.StudentRepository;
import com.backend.questify.Repository.SubmissionRepository;
import com.backend.questify.Util.DtoMapper;
import com.github.codeboy.piston4j.api.CodeFile;
import com.github.codeboy.piston4j.api.ExecutionResult;
import com.github.codeboy.piston4j.api.Piston;
import com.github.codeboy.piston4j.api.Runtime;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class SubmissionService {
	@Autowired
	private SubmissionRepository submissionRepository;

	@Autowired
	private LaboratoryRepository laboratoryRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private UserService userService;

	public Optional<Submission> getSubmission(Long id) {
		return submissionRepository.findById(id);
	}

	@Transactional
	public Submission updateSubmissionContent(Long id, String language, String code) {
		Submission submission = submissionRepository.findById(id)
													.orElseThrow(() -> new RuntimeException("Submission not found"));
		Map<String, String> snippets = submission.getCodeSnippets();
		snippets.put(language, code);
		return submissionRepository.save(submission);
	}

	public Submission saveSubmission(Submission submission) {
		return submissionRepository.save(submission);
	}


	public SubmissionDto getAndCreateSubmission(UUID laboratoryId) {
		Long studentId = userService.getCurrentUserId();

		Optional<Student> studentResult = studentRepository.findById(studentId);
		Student student = studentResult.orElseThrow(
				() -> new ResourceNotFoundException("Student Not Found With This Id: " + studentId));

		Optional<Laboratory> laboratoryResult = laboratoryRepository.findById(laboratoryId);
		Laboratory laboratory = laboratoryResult.orElseThrow(
				() -> new ResourceNotFoundException("Laboratory Not Found With This Id: " + laboratoryId));

		Optional<Submission> existingSubmission = submissionRepository.findByLaboratoryAndStudent(laboratory, student);
		if (existingSubmission.isPresent()) {
			return DtoMapper.INSTANCE.submissionToSubmissionDto(existingSubmission.get());
		}

		Submission newSubmission = Submission.builder()
											 .student(student)
											 .laboratory(laboratory)
											 .build();

		Submission submission = submissionRepository.save(newSubmission);

		SubmissionDto submissionDto = DtoMapper.INSTANCE.submissionToSubmissionDto(submission);

		//! Todo : Debugger said  {
		//    "success": false,
		//    "status": "INTERNAL_SERVER_ERROR",
		//    "message": "\"Cannot invoke \"java.util.Map.isEmpty() because \"this.codeSnippets\" is null",
		//    "error": "NullPointerException",
		//    "data": null }

		return DtoMapper.INSTANCE.submissionToSubmissionDto(submission);
	}

	public void deleteSubmission(Long id) {
		submissionRepository.deleteById(id);
	}

	public ExecutionResponse executeSubmission(UUID laboratoryId, String language) {

		Long studentId = userService.getCurrentUserId();

		Optional<Student> studentResult = studentRepository.findById(studentId);
		Student student = studentResult.orElseThrow(
				() -> new ResourceNotFoundException("Student Not Found With This Id: " + studentId));

		Optional<Laboratory> laboratoryResult = laboratoryRepository.findById(laboratoryId);
		Laboratory laboratory = laboratoryResult.orElseThrow(
				() -> new ResourceNotFoundException("Laboratory Not Found With This Id: " + laboratoryId));

		Optional<Submission> submissionResult = submissionRepository.findByLaboratoryAndStudent(laboratory, student);
		Submission submission = submissionResult.orElseThrow(
				() -> new ResourceNotFoundException("Submission Not Found With Laboratory Id : " + laboratoryId));
		SubmissionDto submissionDto = DtoMapper.INSTANCE.submissionToSubmissionDto(submission);

		ExecutionResult result = getExecutionResult(language, submissionDto);

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

	private static ExecutionResult getExecutionResult(String language, SubmissionDto submissionDto) {
		try {
			Map<String, String> snippets = submissionDto.getCodeSnippets();
			if (!snippets.containsKey(language)) {
				throw new ResourceNotFoundException("Submission For Language : " + language + " Is Not Available");
			}

			Piston api = Piston.getDefaultApi();

			Optional<Runtime> optionalRuntime = api.getRuntime(language);

			Runtime runtime = optionalRuntime.orElseThrow(
					() -> new ResourceNotFoundException("Runtime Not Found With Language : " + language));

			CodeFile codeFile = new CodeFile("questify-coding-space", snippets.get(language));

			return runtime.execute(codeFile);
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}catch (Exception e) {
			throw new BadRequestException("Unexpected Error With Compiler: " + e.getMessage());
		}

	}

}
