package com.backend.questify.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LaboratoryDto {

	private UUID laboratoryId;

	private ProfessorDto professor;

	private AssignmentDtoWithoutProfessor assignment;

	private String labTitle;
	private String description;

	private String problemStatement;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	private int testCaseQuantity;


}
