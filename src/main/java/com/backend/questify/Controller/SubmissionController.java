package com.backend.questify.Controller;

import com.backend.questify.DTO.SubmissionDto;
import com.backend.questify.Entity.Logging;
import com.backend.questify.Entity.Submission;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Model.ApiResponse;
import com.backend.questify.Model.ExecutionResponse;
import com.backend.questify.Model.Language;
import com.backend.questify.Repository.LoggingRepository;
import com.backend.questify.Repository.QuestionRepository;
import com.backend.questify.Repository.SubmissionRepository;
import com.backend.questify.Service.SubmissionService;
import com.backend.questify.Util.EntityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/submission")
public class SubmissionController {

	@Autowired
	private SubmissionService submissionService;

	@Autowired
	private SubmissionRepository submissionRepository;

	@Autowired
	private LoggingRepository loggingRepository;

	@Autowired
	private EntityHelper entityHelper;

	@PutMapping("")
	@PreAuthorize("hasAnyRole('StdAcc', 'ProfAcc')")
	public ResponseEntity<ApiResponse<SubmissionDto>> updateCodeSnippet(@RequestParam UUID questionId, @RequestParam Language language, @RequestBody String codeContent) {
		ApiResponse<SubmissionDto> response = ApiResponse.success(submissionService.updateSubmissionContent(questionId, language.name(), codeContent) , HttpStatus.OK, "Update Submission Successfully");

		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping("")
	@PreAuthorize("hasAnyRole('StdAcc', 'ProfAcc')")
	public ResponseEntity<ApiResponse<SubmissionDto>> getAndCreateSubmission(@RequestParam UUID questionId)
	throws Exception {
		ApiResponse<SubmissionDto> response = ApiResponse.success(submissionService.getAndCreateSubmission(questionId) , HttpStatus.OK, "Get And Create Submission Successfully");
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@DeleteMapping("/reset")
	@PreAuthorize("hasAnyRole('StdAcc', 'ProfAcc')")
	public ResponseEntity<ApiResponse<SubmissionDto>> resetSubmission(@RequestParam UUID questionId) {
		ApiResponse<SubmissionDto> response = ApiResponse.success(submissionService.resetSubmission(questionId) , HttpStatus.OK, "Reset Submission Successfully");
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@PostMapping("/execute")
	@PreAuthorize("hasAnyRole('StdAcc', 'ProfAcc')")
	public ResponseEntity<ApiResponse<ExecutionResponse>> executeSubmission(@RequestParam UUID questionId, @RequestParam String language, @RequestParam UUID testCaseId) {
		ApiResponse<ExecutionResponse> response = ApiResponse.success(submissionService.executeSubmission(questionId, language, testCaseId) , HttpStatus.OK, "Execution Submission Successfully");
		return ResponseEntity.status(response.getStatus()).body(response);
	}


	@PostMapping("/log")
	public Logging logEvent(@RequestParam UUID questionId, @RequestBody Logging logging) {
		//{
		//  "actionName": "copy"
		//}
		Optional<Submission> submissionOpt = submissionRepository.findByQuestionAndStudent(entityHelper.findQuestionById(questionId), entityHelper.findStudentById(entityHelper.getCurrentUserId())); // SUS
		if (submissionOpt.isPresent()) {
			Submission submission = submissionOpt.get();
//			logging.setReport(submission);
			logging.setTimeStamp(LocalDateTime.now());
			return loggingRepository.save(logging);
		} else {
			throw new ResourceNotFoundException("Question not found with id: " + questionId);
		}
	}





	//! Todo : Should Add getRuntime which return language and version available

}

//@RestController
//@RequestMapping("/api/submissions/{submissionId}")
//public class SubmissionController {
//
//	@Autowired
//	private SubmissionService submissionService;
//
//	@GetMapping("/{language}")
//	public ResponseEntity<String> getCode(@PathVariable Long submissionId, @PathVariable String language) {
//		String code = submissionService.getCode(submissionId, language);
//		return ResponseEntity.ok(code);
//	}
//
//	@PostMapping("/{language}")
//	public ResponseEntity<Submission> saveCode(@PathVariable Long submissionId, @PathVariable String language, @RequestBody String code) {
//		Submission submission = submissionService.saveCode(submissionId, language, code);
//		return ResponseEntity.ok(submission);
//	}
//}