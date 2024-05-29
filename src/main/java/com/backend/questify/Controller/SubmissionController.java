package com.backend.questify.Controller;

import com.backend.questify.DTO.SubmissionDto;
import com.backend.questify.Entity.Submission;
import com.backend.questify.Model.ApiResponse;
import com.backend.questify.Model.ExecutionResponse;
import com.backend.questify.Model.Language;
import com.backend.questify.Service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/submission")
public class SubmissionController {

	@Autowired
	private SubmissionService submissionService;

	@PutMapping("")
	@PreAuthorize("hasRole('StdAcc')")
	public ResponseEntity<ApiResponse<SubmissionDto>> updateCodeSnippet(@RequestParam UUID laboratoryId, @RequestParam Language language, @RequestBody String codeContent) {
		SubmissionDto result = submissionService.updateSubmissionContent(laboratoryId, language.name(), codeContent);
		ApiResponse<SubmissionDto> response = ApiResponse.success(result , HttpStatus.OK, "Update Submission Successfully");

		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping("")
	@PreAuthorize("hasRole('StdAcc')")
	public ResponseEntity<ApiResponse<SubmissionDto>> getAndCreateSubmission(@RequestParam UUID laboratoryId) {
		SubmissionDto result = submissionService.getAndCreateSubmission(laboratoryId);
		ApiResponse<SubmissionDto> response = ApiResponse.success(result , HttpStatus.OK, "Get And Create Submission Successfully");

		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping("/languages")
	public ResponseEntity<ApiResponse<Language[]>> getAllLanguages() {
		Language[] languages = Language.values();
		ApiResponse<Language[]> response = ApiResponse.success(languages, HttpStatus.OK, "Fetched all languages successfully");
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@DeleteMapping("/reset")
	@PreAuthorize("hasRole('StdAcc')")
	public ResponseEntity<ApiResponse<SubmissionDto>> resetSubmission(@RequestParam UUID laboratoryId) {
		SubmissionDto result = submissionService.resetSubmission(laboratoryId);
		ApiResponse<SubmissionDto> response = ApiResponse.success(result , HttpStatus.OK, "Reset Submission Successfully");
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@PostMapping("/execute")
	@PreAuthorize("hasRole('StdAcc')")
	public ResponseEntity<ApiResponse<ExecutionResponse>> executeSubmission(@RequestParam UUID laboratoryId, @RequestParam Language language) {
		ExecutionResponse result = submissionService.executeSubmission(laboratoryId, language.name());
		ApiResponse<ExecutionResponse> response = ApiResponse.success(result , HttpStatus.OK, "Execution Submission Successfully");
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