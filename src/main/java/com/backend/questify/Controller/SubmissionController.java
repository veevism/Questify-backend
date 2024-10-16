package com.backend.questify.Controller;

import com.backend.questify.DTO.ReportDto;
import com.backend.questify.DTO.SubmissionDto;
import com.backend.questify.Entity.Logging;
import com.backend.questify.Entity.Report;
import com.backend.questify.Entity.Submission;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Model.*;
import com.backend.questify.Repository.LoggingRepository;
import com.backend.questify.Repository.QuestionRepository;
import com.backend.questify.Repository.ReportRepository;
import com.backend.questify.Repository.SubmissionRepository;
import com.backend.questify.Service.ReportService;
import com.backend.questify.Service.SubmissionService;
import com.backend.questify.Util.DtoMapper;
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
	private ReportRepository reportRepository;

	@Autowired
	private ReportService reportService;

	@Autowired
	private EntityHelper entityHelper;

	@PutMapping("")
	@PreAuthorize("hasAnyRole('StdAcc', 'ProfAcc')")
	// delete submitStatus later
	public ResponseEntity<ApiResponse<SubmissionDto>> updateCodeSnippet(@RequestParam UUID questionId, @RequestParam Language language, @RequestBody String codeContent, @RequestParam SubmissionStatus submissionStatus) {
		ApiResponse<SubmissionDto> response = ApiResponse.success(submissionService.updateSubmissionContent(questionId, language.name(), codeContent, submissionStatus) , HttpStatus.OK, "Update Submission Successfully");

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
	public ResponseEntity<ApiResponse<SubmissionDto>> executeSubmission(@RequestParam UUID questionId, @RequestParam String language) {
		ApiResponse<SubmissionDto> response = ApiResponse.success(submissionService.executeSubmission(questionId, language) , HttpStatus.OK, "Execute Submission Successfully");
		return ResponseEntity.status(response.getStatus()).body(response);
	}


	@PostMapping("/log")
	public  ResponseEntity<ApiResponse<Void>> logEvent(@RequestParam UUID submissionId, @RequestBody Logging logging) {
			reportService.logEvent(submissionId, logging);
			ApiResponse<Void> response = ApiResponse.success(null, HttpStatus.OK, "Log Event Successfully");

			return ResponseEntity.status(response.getStatus()).body(response);
	}

	@PostMapping("/submit")
	public ResponseEntity<ApiResponse<SubmissionDto>> submitSubmission(@RequestParam UUID submissionId) {

		ApiResponse<SubmissionDto> response = ApiResponse.success(submissionService.submitSubmission(submissionId), HttpStatus.OK, "Submit Submission Successfully");

		return ResponseEntity.status(response.getStatus()).body(response);

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
//	public ResponseEntity<String> getCode(@PathVariable UUID submissionId, @PathVariable String language) {
//		String code = submissionService.getCode(submissionId, language);
//		return ResponseEntity.ok(code);
//	}
//
//	@PostMapping("/{language}")
//	public ResponseEntity<Submission> saveCode(@PathVariable UUID submissionId, @PathVariable String language, @RequestBody String code) {
//		Submission submission = submissionService.saveCode(submissionId, language, code);
//		return ResponseEntity.ok(submission);
//	}
//}