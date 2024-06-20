package com.backend.questify.Util;

import com.backend.questify.Entity.*;
import com.backend.questify.Exception.ListIsNotEmptyException;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Exception.UnauthorizedAccessException;
import com.backend.questify.Exception.UserNotAuthenticatedException;
import com.backend.questify.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class EntityHelper {

	@Autowired
	private ProfessorRepository professorRepository;

	@Autowired
	private AssignmentRepository assignmentRepository;

	@Autowired
	private LaboratoryRepository laboratoryRepository;

	@Autowired
	private ClassroomRepository classroomRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private TestCaseRepository testCaseRepository;

	@Autowired
	private UserRepository userRepository;

	public Professor findProfessorById(Long professorId) {
		return professorRepository.findById(professorId)
								  .orElseThrow(() -> new ResourceNotFoundException("Professor not found with Id : " + professorId));
	}

	public Assignment findAssignmentById(UUID assignmentId) {
		return assignmentRepository.findById(assignmentId)
								   .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with Id : " + assignmentId));
	}

	public Laboratory findLaboratoryById(UUID laboratoryId) {
		return laboratoryRepository.findById(laboratoryId)
								   .orElseThrow(() -> new ResourceNotFoundException("Laboratory not found with Id : " + laboratoryId));
	}

	public Classroom findClassroomById(UUID classroomId) {
		return classroomRepository.findById(classroomId).orElseThrow(() -> new ResourceNotFoundException("Classroom not found with Id : " + classroomId));
	}

	public Student findStudentById(Long studentId) {
		return studentRepository.findById(studentId)
						 .orElseThrow(() -> new ResourceNotFoundException("Student not found with Id: " + studentId));
	}

	public List<Laboratory> findLaboratoryByAssignment(Assignment assignment) {
		return laboratoryRepository.findAllByAssignment(assignment);
	}

	public List<Assignment> findAllAssignmentByClassroomId(UUID classroomId) {
		return ListIsNotEmptyException.requireNotEmpty(assignmentRepository.findAllByClassroom_ClassroomId(classroomId), Assignment.class.getSimpleName());
	}

	public TestCase findTestCaseById(UUID testCaseId) {
		return testCaseRepository.findById(testCaseId).orElseThrow(() -> new ResourceNotFoundException("Test Case not found with Id : " + testCaseId));
	}

	public Laboratory findLaboratoryByTestCaseId(UUID testCaseId) {
		UUID laboratoryId = findTestCaseById(testCaseId).getLaboratory()
														.getLaboratoryId();
		return laboratoryRepository.findById(laboratoryId).orElseThrow(() -> new ResourceNotFoundException("Laboratory of this test case not found with Id : " + laboratoryId));
	}

	public Long getCurrentUserId() throws UserNotAuthenticatedException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			try {
				System.out.println(userDetails.getUsername());
				return Long.valueOf(userDetails.getUsername()); // Assuming the username is the userId
			} catch (NumberFormatException e) {
				throw new UserNotAuthenticatedException("Invalid user ID format.");
			}
		}
		throw new UnauthorizedAccessException("User is not authenticated.");
	}

	public User getCurrentUser() {
		Long userId = getCurrentUserId();
		return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
	}

	public User findUserById(Long userId) {
		return userRepository.findById(userId).orElseThrow(
				() -> new ResourceNotFoundException("User Not Found With This Id: " + userId));
	}
}