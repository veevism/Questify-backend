package com.backend.questify.Service;

import com.backend.questify.DTO.LaboratoryDto;
import com.backend.questify.DTO.SubmissionDto;
import com.backend.questify.Entity.*;
import com.backend.questify.Exception.BadRequestException;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Model.ExecutionResponse;
import com.backend.questify.Model.SubmissionStatus;
import com.backend.questify.Repository.*;
import com.backend.questify.Util.DtoMapper;
import com.backend.questify.Util.EntityHelper;
import com.github.codeboy.piston4j.api.*;
import com.github.codeboy.piston4j.api.Runtime;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
	private EntityHelper entityHelper;

	@Autowired
	private ReportRepository reportRepository;

	@Transactional
	public SubmissionDto updateSubmissionContent(UUID questionId, String language, String updatedCode) {
		Submission submission = getCurrentSubmission(questionId);

		Map<String, String> snippets = submission.getCodeSnippets();
		snippets.put(language, updatedCode);

		return convertToDtoWithRemainingTime(submissionRepository.save(submission));
	}

	public SubmissionDto getAndCreateSubmission(UUID questionId) throws Exception {
		Long userId = entityHelper.getCurrentUserId();
		User user = entityHelper.getCurrentUser();

		Question question = entityHelper.findQuestionById(questionId);

		// อาจต้องเปลี่ยนเป็น SubmissionDto ก่อน
		// อาจเกี่ยวกับ status

		Submission submission = null;

		if (user.getRole() == StdAcc) {
			Student student = entityHelper.findStudentById(userId);

			Optional<Submission> existingSubmission = submissionRepository.findByQuestionAndStudent(question, student);
			if (existingSubmission.isPresent()) {
				return convertToDtoWithRemainingTime(existingSubmission.get());
			}

			Submission newSubmission = Submission.builder()
					.student(student)
					.question(question)
					.status(SubmissionStatus.ACTIVE)
					.build();

			submissionRepository.save(newSubmission);

			Report report = Report.builder()
					.submission(newSubmission)
					.maxScore(question.getLaboratory().getMaxScore())
					.build();

			newSubmission.setReport(reportRepository.save(report));

			submission = submissionRepository.save(newSubmission);;

		} else if (user.getRole() == ProfAcc) {
			Professor professor = entityHelper.findProfessorById(userId);

			Optional<Submission> existingSubmission = submissionRepository.findByQuestionAndProfessor(question, professor);
			if (existingSubmission.isPresent()) {
				return convertToDtoWithRemainingTime(submission);
			}

			Submission newSubmission = Submission.builder()
					.professor(professor)
					.question(question)
					.build();

			submission = submissionRepository.save(newSubmission);
		} else {
			throw new Exception("Something Bad Happens To Submission"); //! Todo : Fix This
		}

		return convertToDtoWithRemainingTime(submission);
	}

	public SubmissionDto resetSubmission(UUID questionId) {
		return convertToDtoWithRemainingTime(submissionRepository.save(getCurrentSubmission(questionId)));
	}

	public ExecutionResponse executeSubmission(UUID questionId, String language, UUID testCaseId) {
		SubmissionDto submissionDto = convertToDtoWithRemainingTime(getCurrentSubmission(questionId));

		ExecutionResult result = getExecutionResult(language, submissionDto, entityHelper.findTestCaseById(testCaseId).getInput());

		return ExecutionResponse.builder()
				.StdErr(result.getOutput().getStderr())
				.StdOut(result.getOutput().getStdout())
				.Output(result.getOutput().getOutput())
				.Code(result.getOutput().getCode())
				.Signal(result.getOutput().getSignal())
				.Language(result.getLanguage())
				.Version(result.getVersion())
				.build();
	}

	private Submission getCurrentSubmission(UUID questionId) {
		Long userId = entityHelper.getCurrentUserId();
		User user = entityHelper.getCurrentUser();
		Question question = entityHelper.findQuestionById(questionId);

		Submission submission = null;

		if (user.getRole() == StdAcc) {
			submission = submissionRepository.findByQuestionAndStudent(question, entityHelper.findStudentById(userId))
					.orElseThrow(() -> new ResourceNotFoundException("Submission Not Found With Question Id : " + questionId));
		} else if (user.getRole() == ProfAcc) {
			submission = submissionRepository.findByQuestionAndProfessor(question, entityHelper.findProfessorById(userId))
					.orElseThrow(() -> new ResourceNotFoundException("Submission Not Found With Question Id : " + questionId));
		}

		return submission;
	}

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

			ExecutionRequest request = new ExecutionRequest(runtime.getLanguage(), runtime.getVersion(), codeFile).setStdin(stdin);

			return api.execute(request);
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new BadRequestException("Unexpected Error With Compiler: " + e.getMessage());
		}
	}


	@Transactional
	public SubmissionDto submitSubmission(Long submissionId) {
		Submission submission = submissionRepository.findById(submissionId)
				.orElseThrow(() -> new ResourceNotFoundException("Submission not found with id: " + submissionId));

		// Update submission status
		submission.setStatus(SubmissionStatus.INACTIVE);

		// Find the associated report
		Report report = reportRepository.findBySubmission_SubmissionId(submissionId)
				.orElseThrow(() -> new ResourceNotFoundException("Report not found for submission id: " + submissionId));

		// Mark the report as submitted
		report.markSubmitted();

		// Save both entities
		submissionRepository.save(submission);
		reportRepository.save(report);

		return convertToDtoWithRemainingTime(submission);
	}

	private SubmissionDto convertToDtoWithRemainingTime(Submission submission) {
		SubmissionDto dto = DtoMapper.INSTANCE.submissionToSubmissionDto(submission);
		if (submission.getStartTime() != null && submission.getQuestion() != null) {
			LaboratoryDto lab = dto.getLaboratory();
			if (lab != null && lab.getDurationTime() != null) {
				LocalDateTime endTime = submission.getStartTime().plusSeconds(lab.getDurationTime());
				dto.setRemainingTime((int) ChronoUnit.SECONDS.between(LocalDateTime.now(), endTime));
			}
		}
		return dto;
	}
}
