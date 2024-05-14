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

	public AssignmentDto createAssignment(Assignment assignment, UUID classroomId) {
		System.out.println(assignment);
		Long mockProfessorId = 2L;
		Optional<Professor> result = professorRepository.findById(mockProfessorId);
		Professor professor = result.orElseThrow(() -> new ResourceNotFoundException("Professor not found with Id : " + mockProfessorId));

		Optional<Classroom> classroomResult = classroomRepository.findById(classroomId);

		Classroom classroom = classroomResult.orElseThrow(() -> new ResourceNotFoundException("Classroom not found with Id : " + classroomId));


		Assignment createdAssignment = Assignment.builder()
						.assignmentId(UUID.randomUUID()).score(20)
														 .title(assignment.getTitle())
														 .description(assignment.getDescription())
														 .professor(professor)
														 .isActive(true)
														 .build();

		assignmentRepository.save(createdAssignment);

		System.out.println(DtoMapper.INSTANCE.classroomToClassroomDto(classroom));





//				professor.addAssignment(createdAssignment);
//
//				professorRepository.save(professor);




//		classroomRepository.findBy_ProfessorId
//		Professor professor = result.orElseThrow(() -> new ResourceNotFoundException("Professor not found with Id : " + mockProfessorId));
//		Classroom createdClassroom = Classroom.builder()
//											  .invitationCode(ShortUUIDGenerator.generateShortUUID())
//											  .classroomId(UUID.randomUUID()) // สร้าง UUID ใหม่
//											  .title(classroomDto.getTitle())
//											  .description(classroomDto.getDescription())
//											  .professor(professor)
//											  .isActive(true)
//											  .build();
//		classroomRepository.save(createdClassroom);

//		return DtoMapper.INSTANCE.classroomToClassroomDto(createdClassroom);
		return DtoMapper.INSTANCE.assignmentToAssignmentDto(createdAssignment);
	}

}
