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

	@Autowired
	private UserService userService;



	public LaboratoryDto createLaboratory (UUID assignmentId, LaboratoryDto laboratoryDto) {

		Long professorId = userService.getCurrentUserId();
		Optional<Professor> result = professorRepository.findById(professorId);
		Professor professor = result.orElseThrow(() -> new ResourceNotFoundException("Professor not found with Id : " + professorId));

		Optional<Assignment> assignmentResult = assignmentRepository.findById(assignmentId);
		Assignment assignment = assignmentResult.orElseThrow(() -> new ResourceNotFoundException("Assignment not found with Id : " + assignmentId));

		Laboratory createdLaboratory = Laboratory.builder()
				.assignment(assignment)
				.professor(professor)
				.labTitle(laboratoryDto.getLabTitle())
				.description(laboratoryDto.getDescription())
				.problemStatement(laboratoryDto.getProblemStatement())
				.build();

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

		if (laboratoryDto.getProblemStatement() != null && !laboratoryDto.getProblemStatement().trim().isEmpty()) {
			laboratory.setProblemStatement(laboratoryDto.getProblemStatement());
		}

		laboratoryRepository.save(laboratory);

		return DtoMapper.INSTANCE.laboratoryToLaboratoryDto(laboratory);

	}
}
