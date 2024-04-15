package com.backend.questify.Service;

public class SubmissionService {
}

//@Service
//public class SubmissionService {
//
//	@Autowired
//	private SubmissionRepository submissionRepository;
//
//	public Submission saveCode(Long submissionId, String language, String code) {
//		Submission submission = submissionRepository.findById(submissionId)
//													.orElseThrow(() -> new RuntimeException("Submission not found"));
//
//		Map<String, Object> codeSnippets = new ObjectMapper().readValue(submission.getCodeSnippets(), Map.class);
//		codeSnippets.put(language, code);
//		submission.setCodeSnippets(new ObjectMapper().writeValueAsString(codeSnippets));
//
//		return submissionRepository.save(submission);
//	}
//
//	public String getCode(Long submissionId, String language) {
//		Submission submission = submissionRepository.findById(submissionId)
//													.orElseThrow(() -> new RuntimeException("Submission not found"));
//
//		Map<String, Object> codeSnippets = new ObjectMapper().readValue(submission.getCodeSnippets(), Map.class);
//		return (String) codeSnippets.get(language);
//	}
//}