package com.backend.questify.Service;

import com.backend.questify.DTO.LaboratoryDto;
import com.backend.questify.Entity.*;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Repository.AssignmentRepository;
import com.backend.questify.Repository.LaboratoryRepository;
import com.backend.questify.Repository.ProfessorRepository;
import com.backend.questify.Util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.UUID;

@Service
public class LaboratoryService {

	@Autowired
	private LaboratoryRepository laboratoryRepository;

	@Autowired
	private AssignmentRepository assignmentRepository;

	@Autowired
	private ProfessorRepository professorRepository;



	public LaboratoryDto createLaboratory () {
//		@RequestBody LaboratoryDto laboratoryDto, UUID assignmentId

		Long mockProfessorId = 2L;
		Optional<Professor> result = professorRepository.findById(mockProfessorId);
		Professor professor = result.orElseThrow(() -> new ResourceNotFoundException("Professor not found with Id : " + mockProfessorId));

//		Optional<Assignment> assignmentResult = assignmentRepository.findById(assignmentId);
//		Assignment assignment = assignmentResult.orElseThrow(() -> new ResourceNotFoundException("Assignment not found with Id : " + assignmentId));

		Assignment newAssignment = new Assignment();
		newAssignment.setTitle("New Classroom");
		newAssignment.setDescription("New Classroom");
		assignmentRepository.save(newAssignment);



		Laboratory createdLaboratory = Laboratory.builder()
				.assignment(newAssignment)
				.professor(professor)
				.labTitle("Hi Mom")
				.description("This is laboratory na")
				.build();

		laboratoryRepository.save(createdLaboratory);

		System.out.println("asdasd");
		return DtoMapper.INSTANCE.laboratoryToLaboratoryDto(createdLaboratory);
	}
}
