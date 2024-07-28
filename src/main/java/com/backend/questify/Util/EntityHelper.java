package com.backend.questify.Util;

import com.backend.questify.DTO.StudentDto;
import com.backend.questify.Entity.*;
import com.backend.questify.Exception.ListIsNotEmptyException;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Exception.UnauthorizedAccessException;
import com.backend.questify.Exception.UserNotAuthenticatedException;
import com.backend.questify.Model.Role;
import com.backend.questify.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class EntityHelper {

	@Autowired
	private ProfessorRepository professorRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private LaboratoryRepository laboratoryRepository;

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

	public Student findStudentById(Long studentId) {
		return studentRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found with Id: " + studentId));
	}

	public Question findQuestionById(UUID questionId) {
		return questionRepository.findById(questionId)
								   .orElseThrow(() -> new ResourceNotFoundException("Question not found with Id : " + questionId));
	}

	public List<Question> findQuestionsByLaboratory(Laboratory laboratory) {
		return ListIsNotEmptyException.requireNotEmpty(questionRepository.findAllByLaboratory(laboratory), Question.class.getSimpleName());
	}// .getStudentQuestion().get(getCurrentUser().getStudent().getStudentId())

	public void deleteQuestionById(UUID questionId) {
		questionRepository.deleteById(questionId);
	}

//
//	public List<Question> findQuestions(Laboratory assignment) {
//		return ListIsNotEmptyException.requireNotEmpty(assignmentRepository.findAllByLaboratory_LaboratoryId(laboratoryId), Laboratory.class.getSimpleName());
//	}



	public TestCase findTestCaseById(UUID testCaseId) {
		return testCaseRepository.findById(testCaseId).orElseThrow(() -> new ResourceNotFoundException("Test Case not found with Id : " + testCaseId));
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

	public List<StudentDto> filterStudentsByQuery(List<StudentDto> students, String query) {
		return students.stream()
					   .filter(student -> student.getStudentId().toString().contains(query) ||
							   student.getFirstName_EN().contains(query) ||
							   student.getDisplayName().contains(query))
					   .collect(Collectors.toList());
	}

	public User findUserById(Long userId) {
		return userRepository.findById(userId).orElseThrow(
				() -> new ResourceNotFoundException("User Not Found With This Id: " + userId));
	}

//	public Laboratory findLaboratoryByAssignment(Assignment assignment) {
//		return LaboratoryRepository.find
//	}


//	public List<Student> findStudentsByAssignmentId(UUID assignmentId) {
//
//		return studentRepository.findStudentsByAssignment(findAssignmentById(assignmentId));
//	}

	public List<Laboratory> findLaboratoryByRole(Role role) {
		return null;
	}


	// --------Laboratory
	public void deleteLaboratoryById(UUID laboratoryId) {
		laboratoryRepository.deleteById(laboratoryId);
	}

	public Question findLaboratoryByTestCaseId(UUID testCaseId) {
		UUID laboratoryId = findTestCaseById(testCaseId).getQuestion()
				.getQuestionId();
		return questionRepository.findById(laboratoryId).orElseThrow(() -> new ResourceNotFoundException("Laboratory of this test case not found with Id : " + laboratoryId));
	}

	public Laboratory findLaboratoryById(UUID laboratoryId) {
		return laboratoryRepository.findById(laboratoryId).orElseThrow(() -> new ResourceNotFoundException("Laboratory not found with Id : " + laboratoryId));
	}

	public Laboratory findLaboratoryByInvitationCode(String invitationCode) {
		return laboratoryRepository.findByInvitationCode(invitationCode).orElseThrow(() -> new ResourceNotFoundException("Laboratory not found with this invitation code : " + invitationCode));
	}

	public List<Laboratory> findLaboratoriesByStudent(User user) {
		return ListIsNotEmptyException.requireNotEmpty(laboratoryRepository.findAllByStudent_StudentId(user.getUserId()), Laboratory.class.getSimpleName());
	}

	public List<Laboratory> findLaboratoriesByProfessor(User user) {
		return ListIsNotEmptyException.requireNotEmpty(laboratoryRepository.findAllByProfessor_ProfessorId(user.getUserId()), Laboratory.class.getSimpleName());
	}

	// --------End Laboratory
}