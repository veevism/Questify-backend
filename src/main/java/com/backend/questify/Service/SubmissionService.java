package com.backend.questify.Service;

import com.backend.questify.DTO.SubmissionDto;
import com.backend.questify.Entity.Laboratory;
import com.backend.questify.Entity.Student;
import com.backend.questify.Entity.Submission;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Repository.LaboratoryRepository;
import com.backend.questify.Repository.StudentRepository;
import com.backend.questify.Repository.SubmissionRepository;
import com.backend.questify.Util.DtoMapper;
import com.github.codeboy.piston4j.api.CodeFile;
import com.github.codeboy.piston4j.api.ExecutionResult;
import com.github.codeboy.piston4j.api.Piston;
import com.github.codeboy.piston4j.api.Runtime;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class SubmissionService {
	@Autowired
	private SubmissionRepository submissionRepository;

	@Autowired
	private LaboratoryRepository laboratoryRepository;

	@Autowired
	private StudentRepository studentRepository;

	public Optional<Submission> getSubmission(Long id) {
		return submissionRepository.findById(id);
	}

	@Transactional
	public Submission updateSubmissionContent(Long id, String language, String code) {
		Submission submission = submissionRepository.findById(id).orElseThrow(() -> new RuntimeException("Submission not found"));
		Map<String, String> snippets = submission.getCodeSnippets();
		snippets.put(language, code);
		return submissionRepository.save(submission);
	}

	public Submission saveSubmission(Submission submission) {
		return submissionRepository.save(submission);
	}

	public SubmissionDto createSubmission(UUID laboratoryId) {
//		if (studentOpt.isPresent()) {
//			Student student = studentOpt.get();
		Optional<Student> studentResult = studentRepository.findById(1L);
		Student student = studentResult.orElseThrow(() -> new ResourceNotFoundException("Student Not Found With This Id :"));

		Optional<Laboratory> laboratoryResult = laboratoryRepository.findById(laboratoryId);
		Laboratory laboratory = laboratoryResult.orElseThrow(() -> new ResourceNotFoundException("Laboratory Not Found With This Id : " + laboratoryId));

			Submission newSubmission = new Submission();
			newSubmission.setStudent(student);
			newSubmission.setLaboratory(laboratory);
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
			if (snippets.containsKey(language)) {

		Piston api = Piston.getDefaultApi();
		Optional<Runtime> optionalRuntime = api.getRuntime(language);
		if (optionalRuntime.isPresent()) {
			Runtime runtime = optionalRuntime.get();
			CodeFile codeFile = new CodeFile("main.java", snippets.get(language));
			ExecutionResult result = runtime.execute(codeFile);
			System.out.println(result.getOutput().getStderr());
			System.out.println(result.getOutput().getStdout());
			System.out.println(result.getOutput().getOutput());
			System.out.println(result.getOutput().getCode());
			System.out.println(result.getOutput().getSignal());
		}
			}
		}
		throw new IllegalArgumentException("Submission or language not found");
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