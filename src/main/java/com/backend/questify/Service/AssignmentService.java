package com.backend.questify.Service;

import com.backend.questify.DTO.AssignmentDto;
import com.backend.questify.DTO.ClassroomDto;
import com.backend.questify.DTO.ProfessorDto;
import com.backend.questify.Entity.*;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Exception.UnauthorizedAccessException;
import com.backend.questify.Repository.*;
import com.backend.questify.Util.DtoMapper;
import com.backend.questify.Util.EntityHelper;
import com.backend.questify.Util.ShortUUIDGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class AssignmentService {
	@Autowired
	private AssignmentRepository assignmentRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private EntityHelper entityHelper;



	public AssignmentDto createAssignment(Assignment assignment, UUID classroomId) {
		Long currentUserId = entityHelper.getCurrentUserId();
		Professor professor = entityHelper.findProfessorById(currentUserId);
		Classroom classroom = entityHelper.findClassroomById(classroomId);
		if (!classroom.getProfessor().getProfessorId().equals(currentUserId)) {
			throw new UnauthorizedAccessException("You do not have permission to create assignment in this classroom");
		}
		//! Do This every function in this class


		LocalDateTime now = LocalDateTime.now();
		if (assignment.getStartTime() != null && assignment.getStartTime().isBefore(now)) {
			throw new IllegalArgumentException("Start Date or Due Date cannot be in the past");
		}

		if (assignment.getEndTime() != null && assignment.getEndTime().isBefore(now)) {
			throw new IllegalArgumentException("Start Date or Due Date cannot be in the past");
		}

		if (assignment.getStartTime() != null && assignment.getEndTime() != null && assignment.getEndTime().isBefore(assignment.getStartTime())) {
			throw new IllegalArgumentException("End time cannot be before start time.");
		}



		Assignment createdAssignment = Assignment.builder().score(assignment.getScore())
														 .title(assignment.getTitle())
														 .description(assignment.getDescription())
														 .professor(professor)
														 .classroom(classroom)
														 .isActive(true)
												 		 .startTime(assignment.getStartTime())
												 		 .endTime(assignment.getEndTime())
														 .build();

		return DtoMapper.INSTANCE.assignmentToAssignmentDto(assignmentRepository.save(createdAssignment));
	}

	@Transactional
	public AssignmentDto assignLabToStudent(UUID assignmentId, UUID laboratoryId, Long studentId) {
		Assignment assignment = entityHelper.findAssignmentById(assignmentId);

		assignment.getStudentLabAssignments().put(entityHelper.findStudentById(studentId).getStudentId(), entityHelper.findLaboratoryById(laboratoryId).getLaboratoryId());

		assignmentRepository.save(assignment);

		return DtoMapper.INSTANCE.assignmentToAssignmentDto(assignment);
	}

	//! Todo : Delete student from classroom delete them from assignment too

	@Transactional
	public AssignmentDto randomAssignLabs(UUID assignmentId) {
		Assignment assignment = entityHelper.findAssignmentById(assignmentId);

		assignment.getStudentLabAssignments().clear();
		List<Student> students = assignment.getClassroom().getStudents();
		List<Laboratory> laboratories = entityHelper.findLaboratoryByAssignment(assignment);

		Random random = new Random();
		for (Student student : students) {
			assignment.getStudentLabAssignments().put(student.getStudentId(), laboratories.get(random.nextInt(laboratories.size())).getLaboratoryId());
		}

		return DtoMapper.INSTANCE.assignmentToAssignmentDto(assignmentRepository.save(assignment));
	}

	public List<AssignmentDto> getAssignments(UUID classroomId) {
		return DtoMapper.INSTANCE.assignmentToAssignmentDto(entityHelper.findAllAssignmentByClassroomId(classroomId));
	}

	public AssignmentDto updateAssignment (UUID assignmentId, AssignmentDto assignmentDto) {
		Assignment assignment = entityHelper.findAssignmentById(assignmentId);

		Long currentUserId = entityHelper.getCurrentUserId();
		if (!assignment.getProfessor().getProfessorId().equals(currentUserId)) {
			throw new UnauthorizedAccessException("You do not have permission to update this assignment.");
		}

		LocalDateTime now = LocalDateTime.now();

		LocalDateTime startTime;
		startTime = assignment.getStartTime();

		if (assignmentDto.getStartTime() != null) {
			startTime = assignmentDto.getStartTime();
			assignment.setStartTime(assignmentDto.getStartTime());
		}

		if (assignmentDto.getEndTime() != null) {
			if (assignmentDto.getEndTime().isBefore(now)) {
				throw new IllegalArgumentException("End time cannot be in the past.");
			}
			if (startTime != null && assignmentDto.getEndTime().isBefore(startTime)) {
				throw new IllegalArgumentException("End time cannot be before start time.");
			}
			assignment.setEndTime(assignmentDto.getEndTime());
		}

		if (assignmentDto.getTitle() != null && !assignmentDto.getTitle().trim().isEmpty()) {
			assignment.setTitle(assignmentDto.getTitle());
		}

		if (assignmentDto.getDescription() != null && !assignmentDto.getDescription().trim().isEmpty()) {
			assignment.setDescription(assignmentDto.getDescription());
		}

		if (assignmentDto.getIsActive() != null) {
			assignment.setIsActive(assignmentDto.getIsActive());
		}

		if (assignmentDto.getScore() != null) {
			assignment.setScore(assignmentDto.getScore());
		}



		return DtoMapper.INSTANCE.assignmentToAssignmentDto(assignmentRepository.save(assignment));

	}

	public void deleteAssignment (UUID assignmentId) {
		Assignment assignment = entityHelper.findAssignmentById(assignmentId);
		if (!assignment.getProfessor().getProfessorId().equals(entityHelper.getCurrentUserId())) {
			throw new UnauthorizedAccessException("You do not have permission to delete this assignment.");
		}
			assignmentRepository.deleteById(assignmentId);
	}


	public AssignmentDto getAssignment(UUID assignmentId) {
		return DtoMapper.INSTANCE.assignmentToAssignmentDto(entityHelper.findAssignmentById(assignmentId));

	}
}
