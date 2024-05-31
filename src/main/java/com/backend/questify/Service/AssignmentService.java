package com.backend.questify.Service;

import com.backend.questify.DTO.AssignmentDto;
import com.backend.questify.DTO.ClassroomDto;
import com.backend.questify.DTO.ProfessorDto;
import com.backend.questify.Entity.Assignment;
import com.backend.questify.Entity.Classroom;
import com.backend.questify.Entity.Professor;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Repository.AssignmentRepository;
import com.backend.questify.Repository.ClassroomRepository;
import com.backend.questify.Repository.ProfessorRepository;
import com.backend.questify.Util.DtoMapper;
import com.backend.questify.Util.ShortUUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AssignmentService {
	@Autowired
	private AssignmentRepository assignmentRepository;

	@Autowired
	private ClassroomRepository classroomRepository;

	@Autowired
	private ProfessorRepository professorRepository;

	@Autowired
	private UserService userService;

	public AssignmentDto createAssignment(Assignment assignment, UUID classroomId) {
		Long professorId = userService.getCurrentUserId();
		Optional<Professor> result = professorRepository.findById(professorId);
		Professor professor = result.orElseThrow(() -> new ResourceNotFoundException("Professor not found with Id : " + professorId));

		Optional<Classroom> classroomResult = classroomRepository.findById(classroomId);
		Classroom classroom = classroomResult.orElseThrow(() -> new ResourceNotFoundException("Classroom not found with Id : " + classroomId));

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

//		if (createdAssignment.getClassroom() == null) {
//			System.out.println("Yes");
//		} else {
//
//			System.out.println(DtoMapper.INSTANCE.classroomToClassroomDto(createdAssignment.getClassroom()));
//		}

		assignmentRepository.save(createdAssignment);
		return DtoMapper.INSTANCE.assignmentToAssignmentDto(createdAssignment);
	}

	public List<AssignmentDto> getAssignments(UUID classroomId) {
//		System.out.println(DtoMapper.INSTANCE.assignmentToAssignmentDto(assignmentRepository.findAllByClassroom_ClassroomId(classroomId)));
		List<Assignment> assignments = assignmentRepository.findAllByClassroom_ClassroomId(classroomId);

		if (assignments.isEmpty()) {
			throw new ResourceNotFoundException("Assignment not found");
		}

//		List<AssignmentDto> assignmentDtos = DtoMapper.INSTANCE.assignmentToAssignmentDto(assignments);

		return DtoMapper.INSTANCE.assignmentToAssignmentDto(assignments);
	}

	public AssignmentDto updateAssignment (UUID assignmentId, AssignmentDto assignmentDto) {
		Optional<Assignment> result = assignmentRepository.findByAssignmentId(assignmentId);
		Assignment assignment = result.orElseThrow(() -> new ResourceNotFoundException("Assignment not found with Id : " + assignmentId));

		LocalDateTime now = LocalDateTime.now();

		if (assignmentDto.getStartTime() != null) {
//			if (assignmentDto.getStartTime().isBefore(now)) {
//				throw new IllegalArgumentException("Start time cannot be in the past.");
//			}
			assignment.setStartTime(assignmentDto.getStartTime());
		}

		if (assignmentDto.getEndTime() != null) {
			if (assignmentDto.getEndTime().isBefore(now)) {
				throw new IllegalArgumentException("End time cannot be in the past.");
			}
			if (assignmentDto.getStartTime() != null && assignmentDto.getEndTime().isBefore(assignmentDto.getStartTime())) {
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

		assignmentRepository.save(assignment);

		return DtoMapper.INSTANCE.assignmentToAssignmentDto(assignment);

	}

	public void deleteAssignment (UUID assignmentId) {
		if (assignmentRepository.existsById(assignmentId)) {
			assignmentRepository.deleteById(assignmentId);
		} else {
			throw new ResourceNotFoundException("Assignment not found with Id : " + assignmentId);
		}
	}


	public AssignmentDto getAssignment(UUID assignmentId) {
		Optional<Assignment> result = assignmentRepository.findByAssignmentId(assignmentId);
		Assignment assignment = result.orElseThrow(() -> new ResourceNotFoundException("Assignment not found with Id : " + assignmentId));

		return DtoMapper.INSTANCE.assignmentToAssignmentDto(assignment);

	}
}
