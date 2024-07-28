package com.backend.questify.DTO.testcase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseDto {
	private UUID testCaseId;
	private String input;
	private String expectedOutput;
//	private LaboratoryDtoWithoutRelation laboratory;
}