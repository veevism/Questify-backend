package com.backend.questify.Service;

import com.backend.questify.DTO.LaboratoryDto;
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
import java.util.UUID;

import static com.backend.questify.Model.Role.ProfAcc;
import static com.backend.questify.Model.Role.StdAcc;

@Service
public class LaboratoryService {

	@Autowired
	private LaboratoryRepository laboratoryRepository;

	@Autowired
	private EntityHelper entityHelper;

	public LaboratoryDto createLaboratory(Laboratory laboratory) {
		Professor professor = entityHelper.findProfessorById(entityHelper.getCurrentUserId());

		if (laboratoryRepository.existsByTitleAndProfessor(laboratory.getTitle(), professor)) {
			throw new IllegalArgumentException("Classroom title cannot be duplicated.");
		}

		LocalDateTime now = LocalDateTime.now();

		// ***/120
		Laboratory createdLaboratory = Laboratory.builder()
											  .invitationCode(ShortUUIDGenerator.generateShortUUID())
											  .title(laboratory.getTitle())
											  .description(laboratory.getDescription())
											  .durationTime(laboratory.getDurationTime())
				   	 						  .maxScore(laboratory.getMaxScore())
											  .status(laboratory.getStatus())
											  .professor(professor)
											  .build();

		return DtoMapper.INSTANCE.laboratoryToLaboratoryDto(laboratoryRepository.save(createdLaboratory));
	}

	public LaboratoryDto updateLaboratory(UUID laboratoryId, Laboratory laboratory) {
		Professor professor = entityHelper.findProfessorById(entityHelper.getCurrentUserId());
		Laboratory existingLaboratory = entityHelper.findLaboratoryById(laboratoryId);

		if ((existingLaboratory.getProfessor() != professor)) {
			throw new IllegalArgumentException("Classroom title cannot be duplicated.");
		}
		existingLaboratory.setTitle(laboratory.getTitle());
		existingLaboratory.setDescription(laboratory.getDescription());
		existingLaboratory.setMaxScore(laboratory.getMaxScore());
		existingLaboratory.setDurationTime(laboratory.getDurationTime());
		existingLaboratory.setStatus(laboratory.getStatus());

		return DtoMapper.INSTANCE.laboratoryToLaboratoryDto(laboratoryRepository.save(existingLaboratory));
	}

	public List<LaboratoryDto> getLaboratories() {

		List<Laboratory> laboratories;

		// Get only status Publish
		if (entityHelper.getCurrentUser().getRole().equals(ProfAcc)) {
			laboratories = entityHelper.findLaboratoriesByProfessor(entityHelper.getCurrentUser());
//			classrooms = ListIsNotEmptyException.requireNotEmpty(classroomRepository.findByProfessor_ProfessorId(currentUser.getUserId()), Classroom.class.getSimpleName());
		} else {
			laboratories = entityHelper.findLaboratoriesByStudent(entityHelper.getCurrentUser());

//			classrooms = ListIsNotEmptyException.requireNotEmpty(classroomRepository.findByStudents_StudentId(currentUser.getUserId()), Classroom.class.getSimpleName());
		}


		return DtoMapper.INSTANCE.laboratoryToLaboratoryDto(laboratories);
	}

	public LaboratoryDto getLaboratory(UUID laboratoryId) {
		return DtoMapper.INSTANCE.laboratoryToLaboratoryDto(entityHelper.findLaboratoryById(laboratoryId));
	}

	public void deleteLaboratory(UUID laboratoryId) {
		entityHelper.deleteLaboratoryById(laboratoryId);
	}

	public LaboratoryDto studentJoinLaboratory(String invitationCode) {
		Laboratory existingLaboratory = entityHelper.findLaboratoryByInvitationCode(invitationCode);
		Student student = entityHelper.findStudentById(entityHelper.getCurrentUserId());
//		Optional<Classroom> classroomResult = classroomRepository.findByInvitationCode(invitationCode);
//		Classroom classroom = classroomResult.orElseThrow(() -> new ResourceNotFoundException("Classroom not found with this invitation code : " + invitationCode));
		if (existingLaboratory.getStudents().contains(student)) {
			throw new IllegalArgumentException("Student is already enrolled in this classroom");
		}
		existingLaboratory.addStudentToLaboratory(student);


		return DtoMapper.INSTANCE.laboratoryToLaboratoryDto(laboratoryRepository.save(existingLaboratory));
	}

	public LaboratoryDto removeStudentFromLaboratory(UUID laboratoryId, Long studentId) {

		Laboratory existingLaboratory = entityHelper.findLaboratoryById(laboratoryId);
		existingLaboratory.removeStudentFromLaboratory(entityHelper.findStudentById(studentId));

		return DtoMapper.INSTANCE.laboratoryToLaboratoryDto(laboratoryRepository.save(existingLaboratory));
	}

	@Transactional
	public LaboratoryDto assignQuestionToStudent(UUID laboratoryId, UUID questionId, Long studentId) {
		Laboratory existingLaboratory = entityHelper.findLaboratoryById(laboratoryId);
		existingLaboratory.getStudentQuestion().put(entityHelper.findStudentById(studentId).getStudentId(), entityHelper.findQuestionById(questionId).getQuestionId());

		return DtoMapper.INSTANCE.laboratoryToLaboratoryDto(laboratoryRepository.save(existingLaboratory));
	}
//
	@Transactional
	public LaboratoryDto unAssignQuestionToStudent(UUID laboratoryId, Long studentId) {
		Laboratory existingLaboratory = entityHelper.findLaboratoryById(laboratoryId);

		if (existingLaboratory.getStudentQuestion().containsKey(studentId)) {
			existingLaboratory.getStudentQuestion().remove(studentId);
		} else {
			throw new ResourceNotFoundException("Student with ID " + studentId + " not found in assignment " + laboratoryId);
		}

		return DtoMapper.INSTANCE.laboratoryToLaboratoryDto(laboratoryRepository.save(existingLaboratory));
	}

//	@Transactional
//	public AssignmentDto randomAssignLabs(UUID assignmentId) {
//		Laboratory assignment = entityHelper.findAssignmentById(assignmentId);
//
//		assignment.getStudentLabAssignments().clear();
//		List<Student> students = assignment.getClassroom().getStudents();
//		List<Question> laboratories = entityHelper.findLaboratoryByAssignment(assignment);
//
//		Random random = new Random();
//		for (Student student : students) {
//			assignment.getStudentLabAssignments().put(student.getStudentId(), laboratories.get(random.nextInt(laboratories.size())).getQuestionId());
//		}
//
//		return DtoMapper.INSTANCE.assignmentToAssignmentDto(assignmentRepository.save(assignment));
//	}

}
