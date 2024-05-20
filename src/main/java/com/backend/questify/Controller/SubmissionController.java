package com.backend.questify.Controller;

import com.backend.questify.DTO.SubmissionDto;
import com.backend.questify.Entity.Submission;
import com.backend.questify.Model.ApiResponse;
import com.backend.questify.Service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/submission")
public class SubmissionController {

	@Autowired
	private SubmissionService submissionService;

	//! Don't use submissionId instead use studentId from access_token and laboratoryId
	//! Create Model SubmissionExecuteResponseDto
	@GetMapping("")
	public ResponseEntity<Submission> getSubmission(@RequestParam Long submissionId) {
		return submissionService.getSubmission(submissionId)
								.map(ResponseEntity::ok)
								.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("")
	public ResponseEntity<Submission> updateCodeSnippet(@RequestParam Long submissionId, @RequestParam String language, @RequestBody String codeContent) {
		Submission updatedSubmission = submissionService.updateSubmissionContent(submissionId, language, codeContent);
		return ResponseEntity.ok(updatedSubmission);
	}

	@PostMapping("")
	public ResponseEntity<ApiResponse<SubmissionDto>> createSubmission(@RequestParam UUID laboratoryId) {
		SubmissionDto createdSubmission = submissionService.createSubmission(laboratoryId);
		ApiResponse<SubmissionDto> response = ApiResponse.success(createdSubmission , HttpStatus.CREATED, "Create Submission Successfully");

		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteSubmission(@PathVariable Long id) {
		submissionService.deleteSubmission(id);
		return ResponseEntity.ok("Submission deleted successfully.");
	}

	@GetMapping("/execute")
	public ResponseEntity<String> executeSubmission(@RequestParam Long submissionId, @RequestParam String language) {
		try {
			String result = submissionService.executeSubmission(submissionId, language);
			return ResponseEntity.ok(result);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body("Error: " + e.getMessage());
		}
	}
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