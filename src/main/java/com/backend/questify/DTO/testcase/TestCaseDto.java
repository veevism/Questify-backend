package com.backend.questify.DTO.testcase;

import com.backend.questify.DTO.LaboratoryDto;
import com.backend.questify.Entity.Laboratory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseDto {
	private Long testCaseId;
	private String input;
	private String expectedOutput;
	private LaboratoryDto laboratory;
}