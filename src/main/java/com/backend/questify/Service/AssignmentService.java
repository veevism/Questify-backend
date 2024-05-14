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

	public AssignmentDto createAssignment(Assignment assignment, UUID classroomId) {
//		System.out.println(assignment);
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
														 .classroom(classroom)
														 .isActive(true)
														 .build();

//		if (createdAssignment.getClassroom() == null) {
//			System.out.println("Yes");
//		} else {
//
//			System.out.println(DtoMapper.INSTANCE.classroomToClassroomDto(createdAssignment.getClassroom()));
//		}

		assignmentRepository.save(createdAssignment);




//		classroom.getAssignments().add(createdAssignment);
//
//		classroomRepository.save(classroom);

//		System.out.println(createdAssignment.getClassroom());





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

	public List<AssignmentDto> getAssignments(UUID classroomId) {
//		System.out.println(DtoMapper.INSTANCE.assignmentToAssignmentDto(assignmentRepository.findAllByClassroom_ClassroomId(classroomId)));
		List<Assignment> assignments = assignmentRepository.findAllByClassroom_ClassroomId(classroomId);

		if (assignments.isEmpty()) {
			throw new ResourceNotFoundException("Assignment not found");
		}

//		List<AssignmentDto> assignmentDtos = DtoMapper.INSTANCE.assignmentToAssignmentDto(assignments);

		return DtoMapper.INSTANCE.assignmentToAssignmentDto(assignments);
	}
}
