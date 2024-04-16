package com.backend.questify.Service;

import com.backend.questify.Entity.Student;
import com.backend.questify.Entity.Submission;
import com.backend.questify.Repository.StudentRepository;
import com.backend.questify.Repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public Submission createSubmission(Long studentId) {
		Optional<Student> studentOpt = studentRepository.findById(studentId);
		if (studentOpt.isPresent()) {
			Student student = studentOpt.get();
			Submission newSubmission = new Submission();
			newSubmission.setStudent(student);
			newSubmission.setSubmissionId(22L);
			return submissionRepository.save(newSubmission);
		} else {
			throw new IllegalArgumentException("No student found with ID: " + studentId);
		}
	}

	public void deleteSubmission(Long id) {
		submissionRepository.deleteById(id);
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