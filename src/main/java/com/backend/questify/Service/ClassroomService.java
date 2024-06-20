package com.backend.questify.Service;

import com.backend.questify.DTO.ClassroomDto;
import com.backend.questify.DTO.ProfessorDto;
import com.backend.questify.DTO.SubmissionDto;
import com.backend.questify.Entity.*;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Exception.UnauthorizedAccessException;
import com.backend.questify.Model.Role;
import com.backend.questify.Repository.*;
import com.backend.questify.Util.DtoMapper;
import com.backend.questify.Util.EntityHelper;
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
	private ClassroomRepository classroomRepository;

	@Autowired
	private EntityHelper entityHelper;

//	@Autowired
//	private UserService userService;

	public ClassroomDto createClassroom(ClassroomDto classroomDto) {
		Professor professor = entityHelper.findProfessorById(entityHelper.getCurrentUserId());

		if (classroomRepository.existsByTitleAndProfessor(classroomDto.getTitle(), professor)) {
			throw new IllegalArgumentException("Classroom title cannot be duplicated.");
		}

		Classroom createdClassroom = Classroom.builder()
											  .invitationCode(ShortUUIDGenerator.generateShortUUID())
											  .title(classroomDto.getTitle())
											  .description(classroomDto.getDescription())
											  .professor(professor)
											  .build();

		return DtoMapper.INSTANCE.classroomToClassroomDto(classroomRepository.save(createdClassroom));
	}

	public List<ClassroomDto> getClassrooms() {
		User currentUser = entityHelper.getCurrentUser();
		List<Classroom> classrooms;

		if (currentUser.getRole() == Role.ProfAcc) {
			classrooms = classroomRepository.findByProfessor_ProfessorId(currentUser.getUserId());
		} else if (currentUser.getRole() == Role.StdAcc) {
			classrooms = classroomRepository.findByStudents_StudentId(currentUser.getUserId());
		} else {
			throw new UnauthorizedAccessException("You do not have permission to access classrooms.");
		}
		if (classrooms.isEmpty()) {
			throw new ResourceNotFoundException("Classroom not found");
		}
		return DtoMapper.INSTANCE.classroomToClassroomDto(classrooms);
	}

	public ClassroomDto updateClassroom(UUID classroomId, ClassroomDto classroomDto) {
		Classroom classroom = entityHelper.findClassroomById(classroomId);

		if (!classroom.getProfessor().getProfessorId().equals(entityHelper.getCurrentUserId())) {
			throw new UnauthorizedAccessException("You do not have permission to update this classroom.");
		}

		if (classroomRepository.existsByTitleAndProfessor(classroomDto.getTitle(), classroom.getProfessor())) {
			throw new IllegalArgumentException("Classroom title cannot be duplicated.");
		}

		if (classroomDto.getTitle() == null || classroomDto.getTitle().trim().isEmpty()) {
			throw new IllegalArgumentException("Title cannot be empty");
		}

		if (classroomDto.getDescription() == null || classroomDto.getDescription().trim().isEmpty()) {
			throw new IllegalArgumentException("Description cannot be empty");
		}



		classroom.setTitle(classroomDto.getTitle());
		classroom.setDescription(classroomDto.getDescription());

		return DtoMapper.INSTANCE.classroomToClassroomDto(classroomRepository.save(classroom));
	}

	public ClassroomDto getClassroom(UUID classroomId) {
		return DtoMapper.INSTANCE.classroomToClassroomDto(entityHelper.findClassroomById(classroomId));
	}

	public void deleteClassroom(UUID classroomId) {
		Long currentUserId = entityHelper.getCurrentUserId();

		if (classroomRepository.existsById(classroomId)) {

			Classroom classroom = entityHelper.findClassroomById(classroomId);
			if (!classroom.getProfessor().getProfessorId().equals(currentUserId)) {
				throw new UnauthorizedAccessException("You do not have permission to update this classroom.");
			}

			classroomRepository.deleteById(classroomId);
		} else {
			throw new ResourceNotFoundException("Classroom not found with Id : " + classroomId);
		}
	}

	public ClassroomDto joinClassroom(String invitationCode) {
		Optional<Classroom> classroomResult = classroomRepository.findByInvitationCode(invitationCode);
		Classroom classroom = classroomResult.orElseThrow(() -> new ResourceNotFoundException("Classroom not found with this invitation code : " + invitationCode));

		Student student = entityHelper.findStudentById(entityHelper.getCurrentUserId());

		if (classroom.getStudents().contains(student)) {
			throw new IllegalArgumentException("Student is already enrolled in this classroom");
		}

		classroom.addStudent(student);
		classroomRepository.save(classroom);

		return DtoMapper.INSTANCE.classroomToClassroomDto(classroom);
	}

	public ClassroomDto removeFromClassroom(UUID classroomId, Long studentId) {

		Classroom classroom = entityHelper.findClassroomById(classroomId);

		if (!classroom.getProfessor().getProfessorId().equals(entityHelper.getCurrentUserId())) {
			throw new UnauthorizedAccessException("You do not have permission to update this classroom.");
		}

		Student student = entityHelper.findStudentById(studentId);

		if (!classroom.getStudents().contains(student)) {
			throw new ResourceNotFoundException("Student with Id : " + studentId + " not found in this classroom");
		}

		classroom.removeStudent(student);

		return DtoMapper.INSTANCE.classroomToClassroomDto(classroomRepository.save(classroom));
	}

}
