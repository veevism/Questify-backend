package com.backend.questify.Service;

import com.backend.questify.DTO.SubmissionDto;
import com.backend.questify.Entity.Student;
import com.backend.questify.Entity.Submission;
import com.backend.questify.Repository.StudentRepository;
import com.backend.questify.Repository.SubmissionRepository;
import com.backend.questify.Util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Optional;

@Service
public class SubmissionService {
	@Autowired
	private SubmissionRepository submissionRepository;

	@Autowired
	private StudentRepository studentRepository;

	public Optional<Submission> getSubmissionById(Long id) {
		return submissionRepository.findById(id);
	}

	public Submission updateCodeSnippet(Long id, String language, String code) {
		Submission submission = submissionRepository.findById(id).orElseThrow(() -> new RuntimeException("Submission not found"));
		Map<String, String> snippets = submission.getCodeSnippets();
		snippets.put(language, code);
		return submissionRepository.save(submission);
	}

	public Submission saveSubmission(Submission submission) {
		return submissionRepository.save(submission);
	}

	public SubmissionDto createSubmission(Long userId) {
//		if (studentOpt.isPresent()) {
//			Student student = studentOpt.get();
			Submission newSubmission = new Submission();
			newSubmission.setStudent(studentRepository.findByStudentId(1L));
//		System.out.println(newSubmission);
//			newSubmission.setSubmissionId(22L);
			return DtoMapper.INSTANCE.submissionToSubmissionDto(submissionRepository.save(newSubmission));
//		} else {
//			throw new IllegalArgumentException("No student found with ID: " + userId);
//		}
	}

	public void deleteSubmission(Long id) {
		submissionRepository.deleteById(id);
	}

	public String executeSubmission(Long submissionId, String language) {
		Optional<Submission> submission = submissionRepository.findById(submissionId);
		if (submission.isPresent()) {
			SubmissionDto submissionDto = DtoMapper.INSTANCE.submissionToSubmissionDto(submission.get());
			Map<String, String> snippets = submissionDto.getCodeSnippets();
			System.out.println(snippets);
			if (snippets.containsKey(language)) {
				return executeCode(snippets.get(language), language, "");
			}
		}
		throw new IllegalArgumentException("Submission or language not found");
	}

	private String executeCode(String sourceCode, String language, String input) {
		HttpClient client = HttpClient.newHttpClient();
		String url = "https://emkc.org/api/v2/piston/execute";
		String requestBody = String.format("""
            {
                "language": "%s",
                "files": [{
                    "content": "%s"
                }],
                "stdin": "%s",
                "version": "3.10.0"
            }
            """, language, sourceCode, input);

		HttpRequest request = HttpRequest.newBuilder()
										 .uri(URI.create(url))
										 .header("Content-Type", "application/json")
										 .POST(HttpRequest.BodyPublishers.ofString(requestBody))
										 .build();

		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			return response.body(); // Returning the body directly for simplicity
		} catch (Exception e) {
			e.printStackTrace();
			return null; // Consider appropriate error handling
		}
	}
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