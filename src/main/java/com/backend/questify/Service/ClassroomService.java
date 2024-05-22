package com.backend.questify.Service;

import com.backend.questify.DTO.ClassroomDto;
import com.backend.questify.DTO.ProfessorDto;
import com.backend.questify.DTO.SubmissionDto;
import com.backend.questify.Entity.Classroom;
import com.backend.questify.Entity.Professor;
import com.backend.questify.Entity.Submission;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Exception.UnauthorizedAccessException;
import com.backend.questify.Repository.*;
import com.backend.questify.Util.DtoMapper;
import com.backend.questify.Util.ShortUUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClassroomService {
	@Autowired
	private AssignmentRepository assignmentRepository;

	@Autowired
	private ClassroomRepository classroomRepository;

	@Autowired
	private ProfessorRepository professorRepository;

	@Autowired
	private UserService userService;


//	@Autowired
//	private ProfessorService

//	public ClassroomDto createClassroom() {
//
//	}

	public ClassroomDto createClassroom(ClassroomDto classroomDto) {
		Long professorId = userService.getCurrentUserId();
		Optional<Professor> result = professorRepository.findById(professorId);
		Professor professor = result.orElseThrow(() -> new ResourceNotFoundException("Professor not found with Id : " + professorId));
		Classroom createdClassroom = Classroom.builder()
											  .invitationCode(ShortUUIDGenerator.generateShortUUID())
											  .title(classroomDto.getTitle())
											  .description(classroomDto.getDescription())
											  .professor(professor)
											  .isActive(true)
											  .build();
		classroomRepository.save(createdClassroom);

		//		ProfessorDto professorsDto = DtoMapper.INSTANCE.professorToProfessorDto(professor);
		return DtoMapper.INSTANCE.classroomToClassroomDto(createdClassroom);
	}

	public List<ClassroomDto> getClassrooms() {
		List<Classroom> classrooms = classroomRepository.findAll();
		if (classrooms.isEmpty()) {
			throw new ResourceNotFoundException("Classroom not found");
		}
		return DtoMapper.INSTANCE.classroomToClassroomDto(classrooms);
	}

	public ClassroomDto updateClassroom(UUID classroomId, ClassroomDto classroomDto) {
		Long currentUserId = userService.getCurrentUserId();
		Optional<Classroom> result = classroomRepository.findById(classroomId);
		Classroom classroom = result.orElseThrow(() -> new ResourceNotFoundException("Classroom not found with Id : " + classroomId));

		if (!classroom.getProfessor().getProfessorId().equals(currentUserId)) {
			throw new UnauthorizedAccessException("You do not have permission to update this classroom.");
		}

		if (classroomDto.getTitle() == null || classroomDto.getTitle().trim().isEmpty()) {
			throw new IllegalArgumentException("Title cannot be empty");
		}

		if (classroomDto.getDescription() == null || classroomDto.getDescription().trim().isEmpty()) {
			throw new IllegalArgumentException("Description cannot be empty");
		}



		classroom.setTitle(classroomDto.getTitle());
		classroom.setDescription(classroomDto.getDescription());

		classroomRepository.save(classroom);

		return DtoMapper.INSTANCE.classroomToClassroomDto(classroom);
	}

	public ClassroomDto getClassroom(UUID classroomId) {
		Optional<Classroom> result = classroomRepository.findById(classroomId);
		Classroom classroom = result.orElseThrow(() -> new ResourceNotFoundException("Classroom not found with Id : " + classroomId));
		return DtoMapper.INSTANCE.classroomToClassroomDto(classroom);
	}

	public void deleteClassroom(UUID classroomId) {
		Long currentUserId = userService.getCurrentUserId();


		if (classroomRepository.existsById(classroomId)) {
			Optional<Classroom> result = classroomRepository.findById(classroomId);
			Classroom classroom = result.orElseThrow(() -> new ResourceNotFoundException("Classroom not found with Id : " + classroomId));

			if (!classroom.getProfessor().getProfessorId().equals(currentUserId)) {
				throw new UnauthorizedAccessException("You do not have permission to update this classroom.");
			}

			classroomRepository.deleteById(classroomId);
		} else {
			throw new ResourceNotFoundException("Classroom not found with Id : " + classroomId);
		}
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
