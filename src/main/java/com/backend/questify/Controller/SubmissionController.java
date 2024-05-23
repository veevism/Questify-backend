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

	//! Don't use submissionId instead use studentId from access_token and laboratoryId
	//! Create Model SubmissionExecuteResponseDto
//	@GetMapping("")
//	public ResponseEntity<Submission> getSubmission(@RequestParam Long submissionId) {
//		return submissionService.getSubmission(submissionId)
//								.map(ResponseEntity::ok)
//								.orElseGet(() -> ResponseEntity.notFound().build());
//	}

	@PutMapping("")
	@PreAuthorize("hasRole('StdAcc')")
	public ResponseEntity<Submission> updateCodeSnippet(@RequestParam Long submissionId, @RequestParam String language, @RequestBody String codeContent) {
		Submission updatedSubmission = submissionService.updateSubmissionContent(submissionId, language, codeContent);
		return ResponseEntity.ok(updatedSubmission);
	}

	//! Todo : Use Laboratory id only for accessing submission element
	// because in backend they doesn't have submission to click in

	//! Todo : Or Not? They can just find one from laboratory, then places it in params
	// then use it everytime, this seems to be the best practice

	// Submission can have only one per laboratory
	@GetMapping("")
	@PreAuthorize("hasRole('StdAcc')")
	public ResponseEntity<ApiResponse<SubmissionDto>> getAndCreateSubmission(@RequestParam UUID laboratoryId) {
		SubmissionDto createdSubmission = submissionService.getAndCreateSubmission(laboratoryId);
		ApiResponse<SubmissionDto> response = ApiResponse.success(createdSubmission , HttpStatus.OK, "Get And Create Submission Successfully");

		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('StdAcc')")
	public ResponseEntity<String> resetSubmission(@PathVariable Long id) {
		submissionService.deleteSubmission(id);
		return ResponseEntity.ok("Submission deleted successfully.");
	}

	@PostMapping("/execute")
	@PreAuthorize("hasRole('StdAcc')")
	public ResponseEntity<ApiResponse<ExecutionResponse>> executeSubmission(@RequestParam UUID laboratoryId, @RequestParam Language language) {
		ExecutionResponse result = submissionService.executeSubmission(laboratoryId, language.name());
		ApiResponse<ExecutionResponse> response = ApiResponse.success(result , HttpStatus.OK, "Execution Successfully");
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