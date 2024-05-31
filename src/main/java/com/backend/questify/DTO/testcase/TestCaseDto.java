package com.backend.questify.DTO.testcase;

import com.backend.questify.DTO.LaboratoryDto;
import com.backend.questify.DTO.LaboratoryDtoWithoutRelation;
import com.backend.questify.Entity.Laboratory;
import jakarta.persistence.*;
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
	private LaboratoryDtoWithoutRelation laboratory;
}