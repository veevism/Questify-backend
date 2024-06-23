package com.backend.questify.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentWithLaboratory {
//	private StudentDto student;
	private Long studentId;
	private String email;
	private String firstName_EN;
	private String displayName;
	private UUID laboratoryId;
	private String labTitle;

}
