package com.backend.questify.Controller;

public class SubmissionController {
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