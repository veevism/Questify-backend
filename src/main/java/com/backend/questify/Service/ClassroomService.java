package com.backend.questify.Service;

import com.backend.questify.DTO.SubmissionDto;
import com.backend.questify.Entity.Submission;
import com.backend.questify.Repository.AssignmentRepository;
import com.backend.questify.Repository.ClassroomRepository;
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
public class ClassroomService {
	@Autowired
	private AssignmentRepository assignmentRepository;

	@Autowired
	private ClassroomRepository classroomRepository;

//	@Autowired
//	private ProfessorService

//	public ClassroomDto createClassroom() {
//
//	}

	public void createClassroom() {

		System.out.println("Hello");
	}

//
//	public SubmissionDto createSubmission(Long userId) {
////		if (studentOpt.isPresent()) {
////			Student student = studentOpt.get();
//		Submission newSubmission = new Submission();
//		newSubmission.setStudent(studentRepository.findByStudentId(1L));
////		System.out.println(newSubmission);
////			newSubmission.setSubmissionId(22L);
//		return DtoMapper.INSTANCE.submissionToSubmissionDto(submissionRepository.save(newSubmission));
////		} else {
////			throw new IllegalArgumentException("No student found with ID: " + userId);
////		}
//	}
//
//	public String executeSubmission(Long submissionId, String language) {
//		Optional<Submission> submission = submissionRepository.findById(submissionId);
//		if (submission.isPresent()) {
//			SubmissionDto submissionDto = DtoMapper.INSTANCE.submissionToSubmissionDto(submission.get());
//			Map<String, String> snippets = submissionDto.getCodeSnippets();
//			System.out.println(snippets);
//			if (snippets.containsKey(language)) {
//				return executeCode(snippets.get(language), language, "");
//			}
//		}
//		throw new IllegalArgumentException("Submission or language not found");
//	}

}
