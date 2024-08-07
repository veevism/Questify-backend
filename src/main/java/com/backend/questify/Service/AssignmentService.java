package com.backend.questify.Service;

import com.backend.questify.DTO.AssignmentDto;
import com.backend.questify.Entity.*;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Exception.UnauthorizedAccessException;
import com.backend.questify.Repository.*;
import com.backend.questify.Util.DtoMapper;
import com.backend.questify.Util.EntityHelper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;


public class AssignmentService {
//	@Autowired
//	private AssignmentRepository assignmentRepository;
//
//	@Autowired
//	private EntityHelper entityHelper;
//
//	public AssignmentDto createAssignment(Laboratory assignment, UUID classroomId) {
//		Long currentUserId = entityHelper.getCurrentUserId();
//		Professor professor = entityHelper.findProfessorById(currentUserId);
//		Classroom classroom = entityHelper.findClassroomById(classroomId);
//		//! Do This every function in this class
//
//
//		LocalDateTime now = LocalDateTime.now();
//		if (assignment.getStartTime() != null && assignment.getStartTime().isBefore(now)) {
//			throw new IllegalArgumentException("Start Date or Due Date cannot be in the past");
//		}
//
//		if (assignment.getEndTime() != null && assignment.getEndTime().isBefore(now)) {
//			throw new IllegalArgumentException("Start Date or Due Date cannot be in the past");
//		}
//
//		if (assignment.getStartTime() != null && assignment.getEndTime() != null && assignment.getEndTime().isBefore(assignment.getStartTime())) {
//			throw new IllegalArgumentException("End time cannot be before start time.");
//		}
//
//
//
//		Laboratory createdAssignment = Laboratory.builder().score(assignment.getScore())
//														 .title(assignment.getTitle())
//														 .description(assignment.getDescription())
//														 .professor(professor)
//														 .classroom(classroom)
//														 .status(assignment.getStatus())
//												 		 .startTime(assignment.getStartTime())
//												 		 .endTime(assignment.getEndTime())
//														 .build();
//
//		return DtoMapper.INSTANCE.assignmentToAssignmentDto(assignmentRepository.save(createdAssignment));
//	}
//
//
//
//	@Transactional
//	public AssignmentDto unAssignedLabToStudents(UUID assignmentId) {
//		Laboratory assignment = entityHelper.findAssignmentById(assignmentId);
//
//		assignment.getStudentLabAssignments().clear();
//
//		return DtoMapper.INSTANCE.assignmentToAssignmentDto(assignmentRepository.save(assignment));
//	}
//
//	//! Todo : Delete student from classroom delete them from assignment too
//
//
//
//	public List<AssignmentDto> getAssignments(UUID classroomId) {
//		return DtoMapper.INSTANCE.assignmentToAssignmentDto(entityHelper.findAllAssignmentByClassroomId(classroomId));
//	}
//
//	public AssignmentDto updateAssignment (UUID assignmentId, AssignmentDto assignmentDto) {
//		Laboratory assignment = entityHelper.findAssignmentById(assignmentId);
//
//		Long currentUserId = entityHelper.getCurrentUserId();
//		if (!assignment.getProfessor().getProfessorId().equals(currentUserId)) {
//			throw new UnauthorizedAccessException("You do not have permission to update this assignment.");
//		}
//
//
//		if (assignmentDto.getTitle() != null && !assignmentDto.getTitle().trim().isEmpty()) {
//			assignment.setTitle(assignmentDto.getTitle());
//		}
//
//		if (assignmentDto.getDescription() != null && !assignmentDto.getDescription().trim().isEmpty()) {
//			assignment.setDescription(assignmentDto.getDescription());
//		}
//
//		if (assignmentDto.getStatus() != null) {
//			assignment.setStatus(assignmentDto.getStatus());
//		}
//
//		if (assignmentDto.getScore() != null) {
//			assignment.setScore(assignmentDto.getScore());
//		}
//
//
//
//		return DtoMapper.INSTANCE.assignmentToAssignmentDto(assignmentRepository.save(assignment));
//
//	}
//
//	public void deleteAssignment (UUID assignmentId) {
//		Laboratory assignment = entityHelper.findAssignmentById(assignmentId);
//		if (!assignment.getProfessor().getProfessorId().equals(entityHelper.getCurrentUserId())) {
//			throw new UnauthorizedAccessException("You do not have permission to delete this assignment.");
//		}
//			assignmentRepository.deleteById(assignmentId);
//	}
//
//
//	public AssignmentDto getAssignment(UUID assignmentId) {
//		return DtoMapper.INSTANCE.assignmentToAssignmentDto(entityHelper.findAssignmentById(assignmentId));
//
//	}
}
