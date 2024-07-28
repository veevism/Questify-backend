package com.backend.questify.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomDto {
	private UUID classroomId;

	private ProfessorDto professor;

	private String title;

	private String description;

	private String invitationCode;

	private int studentQuantity;

	private List<StudentDto> students;

//	private Set<AssignmentDto> assignments;
}
