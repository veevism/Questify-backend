package com.backend.questify.Service;

import com.backend.questify.DTO.StudentWithLaboratory;
import com.backend.questify.Entity.Question;
import com.backend.questify.Entity.Student;
import com.backend.questify.Exception.ListIsNotEmptyException;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Util.EntityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentService {

	@Autowired
	private EntityHelper entityHelper;

//	public List<StudentWithLaboratory> getStudents(UUID classroomId, UUID assignmentId, String query) {
//		if (assignmentId != null) {
//			return ListIsNotEmptyException.requireNotEmpty(getStudentsWithLaboratoriesByAssignment(assignmentId),Student.class.getSimpleName());
//
//		}
////			entityHelper.filterStudentsByQuery(), query);
////		} else if (classroomId != null) {
////			entityHelper.filterStudentsByQuery(entityHelper.findStudentsByClassroom(entityHelper.findClassroomById(classroomId)), query);
////		}
//		throw new ResourceNotFoundException("Don't know");
//	}


//	public List<StudentWithLaboratory> getStudentsWithLaboratoriesByAssignment(UUID assignmentId) {
//		List<Student> students = entityHelper.findStudentsByAssignmentId(assignmentId);
//
//		return students.stream().map(student -> {
//			UUID laboratoryId = entityHelper.findAssignmentById(assignmentId).getStudentLabAssignments().get(student.getStudentId());
//			Laboratory laboratory = entityHelper.findLaboratoryById(laboratoryId);
//			return StudentWithLaboratory.builder()
//										.student(StudentDto.builder()
//														   .studentId(student.getStudentId())
//														   .email(student.getUser().getEmail())
//														   .firstName_EN(student.getUser().getFirstName_EN())
//														   .displayName(student.getUser().getDisplayName())
//														   .build())
//										.laboratoryId(laboratoryId)
//										.labTitle(laboratory.getLabTitle())
//										.build();
//		}).collect(Collectors.toList());
//	}

//	public List<StudentWithLaboratory> getStudentsWithLaboratoriesByAssignment(UUID questionId) {
//		List<Student> students = entityHelper.findStudentsByAssignmentId(assignmentId);
//
//		return students.stream().map(student -> {
//			UUID laboratoryId = entityHelper.findAssignmentById(assignmentId).getStudentLabAssignments().get(student.getStudentId());
//			Question question = questionId != null ? entityHelper.findQuestionById(questionId) : null;
//
//			return StudentWithLaboratory.builder()
//					.studentId(student.getStudentId())
//					.email(student.getUser().getEmail())
//					.firstName_EN(student.getUser().getFirstName_EN())
//					.displayName(student.getUser().getDisplayName())
//					.laboratoryId(laboratoryId)
//					.labTitle(question != null ? question.getTitle() : null)
//					.build();
//		}).collect(Collectors.toList());
//	}
}
