package com.backend.questify.Util;

import com.backend.questify.Entity.*;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
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
}