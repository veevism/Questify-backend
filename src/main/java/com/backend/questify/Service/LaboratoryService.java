package com.backend.questify.Service;

import com.backend.questify.DTO.LaboratoryDto;
import com.backend.questify.Entity.*;
import com.backend.questify.Exception.BadRequestException;
import com.backend.questify.Exception.ListIsNotEmptyException;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Repository.*;
import com.backend.questify.Util.DtoMapper;
import com.backend.questify.Util.EntityHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.backend.questify.Model.Role.ProfAcc;
import static com.backend.questify.Model.Role.StdAcc;

@Service
public class LaboratoryService {

	@Autowired
	private LaboratoryRepository laboratoryRepository;

	@Autowired
	private EntityHelper entityHelper;

	public LaboratoryDto createLaboratory (UUID assignmentId, LaboratoryDto laboratoryDto) {

		Laboratory createdLaboratory = Laboratory.builder()
				.assignment(entityHelper.findAssignmentById(assignmentId))
				.professor(entityHelper.findProfessorById(entityHelper.getCurrentUserId()))
				.labTitle(laboratoryDto.getLabTitle())
				.description(laboratoryDto.getDescription())
				.problemStatement(laboratoryDto.getProblemStatement())
				.build();

		return DtoMapper.INSTANCE.laboratoryToLaboratoryDto(laboratoryRepository.save(createdLaboratory));
	}

	public List<LaboratoryDto> getLaboratories(UUID assignmentId) {
		User user = entityHelper.getCurrentUser();
		Assignment assignment = entityHelper.findAssignmentById(assignmentId);
		if (user.getRole() == StdAcc) {
			UUID laboratoryId = assignment.getStudentLabAssignments().get(user.getStudent().getStudentId());
			return DtoMapper.INSTANCE.laboratoryToLaboratoryDto(ListIsNotEmptyException.requireNotEmpty(laboratoryRepository.findAllByLaboratoryId(laboratoryId), Laboratory.class.getSimpleName()));

		} else if (user.getRole() == ProfAcc) {
			return DtoMapper.INSTANCE.laboratoryToLaboratoryDto(ListIsNotEmptyException.requireNotEmpty(laboratoryRepository.findAllByAssignment(assignment), Laboratory.class.getSimpleName()));
		} else {
			throw new BadRequestException("Bad User"); //! Todo : Cope with this
		}
	}

	//
	public LaboratoryDto getLaboratory(UUID laboratoryId) {
		return DtoMapper.INSTANCE.laboratoryToLaboratoryDto(entityHelper.findLaboratoryById(laboratoryId));
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

		Laboratory laboratory = entityHelper.findLaboratoryById(laboratoryId);

		if (laboratoryDto.getLabTitle() != null && !laboratoryDto.getLabTitle().trim().isEmpty()) {
			laboratory.setLabTitle(laboratoryDto.getLabTitle());
		}

		if (laboratoryDto.getDescription() != null && !laboratoryDto.getDescription().trim().isEmpty()) {
			laboratory.setDescription(laboratoryDto.getDescription());
		}

		if (laboratoryDto.getProblemStatement() != null && !laboratoryDto.getProblemStatement().trim().isEmpty()) {
			laboratory.setProblemStatement(laboratoryDto.getProblemStatement());
		}

		return DtoMapper.INSTANCE.laboratoryToLaboratoryDto(laboratoryRepository.save(laboratory));

	}
}
