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
public class LaboratoryDtoWithoutRelation {

	private UUID laboratoryId;

	private ProfessorDto professor;

	private String labTitle;
	private String description;

	private String problemStatement;
	private String inputFormat;
	private String outputFormat;
	private String sampleInput;
	private String sampleOutput;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	private int testCaseQuantity;


}
