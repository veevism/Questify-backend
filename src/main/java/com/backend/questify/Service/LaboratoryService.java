package com.backend.questify.Service;

import com.backend.questify.DTO.LaboratoryDto;
import com.backend.questify.Entity.*;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Repository.AssignmentRepository;
import com.backend.questify.Repository.ClassroomRepository;
import com.backend.questify.Repository.LaboratoryRepository;
import com.backend.questify.Repository.ProfessorRepository;
import com.backend.questify.Util.DtoMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
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

	@Autowired
	private ClassroomRepository classroomRepository;

//	@PersistenceContext
//	private EntityManager entityManager;



	public LaboratoryDto createLaboratory (UUID assignmentId, LaboratoryDto laboratoryDto) {
//		@RequestBody LaboratoryDto laboratoryDto, UUID assignmentId

		Long mockProfessorId = 2L;
		Optional<Professor> result = professorRepository.findById(mockProfessorId);
		Professor professor = result.orElseThrow(() -> new ResourceNotFoundException("Professor not found with Id : " + mockProfessorId));

		Optional<Assignment> assignmentResult = assignmentRepository.findById(assignmentId);
		Assignment assignment = assignmentResult.orElseThrow(() -> new ResourceNotFoundException("Assignment not found with Id : " + assignmentId));



//		Assignment newAssignment = new Assignment();
//		newAssignment.setTitle("New Classroom");
//		newAssignment.setDescription("New Classroom");
//		assignmentRepository.save(newAssignment);



		Laboratory createdLaboratory = Laboratory.builder()
				.assignment(assignment)
				.professor(professor)
				.labTitle(laboratoryDto.getLabTitle())
				.description(laboratoryDto.getDescription())
				.inputFormat(laboratoryDto.getInputFormat())
				.outputFormat(laboratoryDto.getOutputFormat())
				.sampleInput(laboratoryDto.getSampleInput())
				.sampleOutput(laboratoryDto.getSampleOutput())
				.build();

//		TestCase testCase1 = new TestCase();
//		testCase1.setInput("Input 1");
//		testCase1.setExpectedOutput("Output 1");
//
//
//		TestCase testCase2 = new TestCase();
//		testCase2.setInput("Input 2");
//		testCase2.setExpectedOutput("Output 2");
//
//		createdLaboratory.addTestCase(testCase1);
//		createdLaboratory.addTestCase(testCase2);

		laboratoryRepository.save(createdLaboratory);

		return DtoMapper.INSTANCE.laboratoryToLaboratoryDto(createdLaboratory);
	}

	public List<LaboratoryDto> getLaboratories(UUID assignmentId) {

		List<Laboratory> laboratories = laboratoryRepository.findAllByAssignment_AssignmentId(assignmentId);

		if (laboratories.isEmpty()) {
			throw new ResourceNotFoundException("Laboratories not found");

		}

		return DtoMapper.INSTANCE.laboratoryToLaboratoryDto(laboratories);

	}

	public LaboratoryDto getLaboratory(UUID laboratoryId) {
		Optional<Laboratory> result = laboratoryRepository.findById(laboratoryId);

		Laboratory laboratory = result.orElseThrow(() -> new ResourceNotFoundException
				("Laboratory not found with Id : " + laboratoryId));

		return DtoMapper.INSTANCE.laboratoryToLaboratoryDto(laboratory);
	}

	public void deleteLaboratory(UUID laboratoryId) {
		if (laboratoryRepository.existsById(laboratoryId)) {
			laboratoryRepository.deleteById(laboratoryId);
		} else {
			throw new ResourceNotFoundException("Laboratory not found with Id : " + laboratoryId);
		}
	}

	@Transactional
	public LaboratoryDto updateLaboratory(UUID laboratoryId, LaboratoryDto laboratoryDto) {
		Optional<Laboratory> result = laboratoryRepository.findById(laboratoryId);

		Laboratory laboratory = result.orElseThrow(() -> new ResourceNotFoundException
				("Laboratory not found with Id : " + laboratoryId));

		if (laboratoryDto.getLabTitle() != null && !laboratoryDto.getLabTitle().trim().isEmpty()) {
			laboratory.setLabTitle(laboratoryDto.getLabTitle());
		}

		if (laboratoryDto.getDescription() != null && !laboratoryDto.getDescription().trim().isEmpty()) {
			laboratory.setDescription(laboratoryDto.getDescription());
		}

		if (laboratoryDto.getStartTime() != null) {
			laboratory.setStartTime(laboratoryDto.getStartTime());
		}

		if (laboratoryDto.getEndTime() != null) {
			laboratory.setEndTime(laboratoryDto.getEndTime());
		}

		if (laboratoryDto.getProblemStatement() != null && !laboratoryDto.getProblemStatement().trim().isEmpty()) {
			laboratory.setProblemStatement(laboratoryDto.getProblemStatement());
		}

		if (laboratoryDto.getInputFormat() != null && !laboratoryDto.getInputFormat().trim().isEmpty()) {
			laboratory.setInputFormat(laboratoryDto.getInputFormat());
		}

		if (laboratoryDto.getOutputFormat() != null && !laboratoryDto.getOutputFormat().trim().isEmpty()) {
			laboratory.setOutputFormat(laboratoryDto.getOutputFormat());
		}

		if (laboratoryDto.getSampleInput() != null && !laboratoryDto.getSampleInput().trim().isEmpty()) {
			laboratory.setSampleInput(laboratoryDto.getSampleInput());
		}

		if (laboratoryDto.getSampleOutput() != null && !laboratoryDto.getSampleOutput().trim().isEmpty()) {
			laboratory.setSampleOutput(laboratoryDto.getSampleOutput());
		}

//		laboratoryRepository.save(laboratory);
		laboratoryRepository.save(laboratory);

		return DtoMapper.INSTANCE.laboratoryToLaboratoryDto(laboratory);

	}
}
