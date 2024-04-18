package com.backend.questify.Controller;

import com.backend.questify.DTO.SubmissionDto;
import com.backend.questify.Entity.Submission;
import com.backend.questify.Service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/submission")
public class SubmissionController {

	@Autowired
	private SubmissionService submissionService;

	@GetMapping("/{id}")
	public ResponseEntity<Submission> getSubmissionById(@PathVariable Long id) {
		return submissionService.getSubmissionById(id)
								.map(ResponseEntity::ok)
								.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}/{language}")
	public ResponseEntity<Submission> updateCodeSnippet(@PathVariable Long id, @PathVariable String language, @RequestBody String code) {
		Submission updatedSubmission = submissionService.updateCodeSnippet(id, language, code);
		return ResponseEntity.ok(updatedSubmission);
	}

	@PostMapping("/{id}")
	public ResponseEntity<SubmissionDto> createSubmission(@PathVariable Long id) {
		SubmissionDto savedSubmission = submissionService.createSubmission(id);
		return ResponseEntity.ok(savedSubmission);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteSubmission(@PathVariable Long id) {
		submissionService.deleteSubmission(id);
		return ResponseEntity.ok("Submission deleted successfully.");
	}
	// Other endpoints...
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