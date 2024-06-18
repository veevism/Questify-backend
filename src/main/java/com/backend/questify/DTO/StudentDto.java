package com.backend.questify.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
	private Long studentId;
	private String email;
	private String firstName_EN;
	private String displayName;
	// add something Web requested
}
