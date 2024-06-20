package com.backend.questify.Service;

import com.backend.questify.DTO.LaboratoryDto;
import com.backend.questify.Entity.*;
import com.backend.questify.Exception.BadRequestException;
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
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private EntityHelper entityHelper;

	private User getCurrentUser() {
		Long userId = userService.getCurrentUserId();
		return userRepository.findById(userId)
							 .orElseThrow(() -> new ResourceNotFoundException("User Not Found With This Id: " + userId));
	}

	public LaboratoryDto createLaboratory (UUID assignmentId, LaboratoryDto laboratoryDto) {

		Long professorId = userService.getCurrentUserId();
		Professor professor = entityHelper.findProfessorById(professorId);
		Assignment assignment = entityHelper.findAssignmentById(assignmentId);

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
		User user = getCurrentUser();
		Assignment assignment = entityHelper.findAssignmentById(assignmentId);
		if (user.getRole() == StdAcc) {
			Student student = user.getStudent();
			UUID laboratoryId = assignment.getStudentLabAssignments().get(student.getStudentId());
			List<Laboratory> laboratories = laboratoryRepository.findAllByLaboratoryId(laboratoryId);
			if (laboratories.isEmpty()) {
				throw new ResourceNotFoundException("Laboratories not found");
			}
			return DtoMapper.INSTANCE.laboratoryToLaboratoryDto(laboratories);

		} else if (user.getRole() == ProfAcc) {
			List<Laboratory> laboratories = laboratoryRepository.findAllByAssignment(assignment);
			if (laboratories.isEmpty()) {
				throw new ResourceNotFoundException("Laboratories not found");
			}
			return DtoMapper.INSTANCE.laboratoryToLaboratoryDto(laboratories);

		} else {
			throw new BadRequestException("Bad User"); //! Todo : Cope with this
		}
	}

	//
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

		laboratoryRepository.save(laboratory);

		return DtoMapper.INSTANCE.laboratoryToLaboratoryDto(laboratory);

	}
}
