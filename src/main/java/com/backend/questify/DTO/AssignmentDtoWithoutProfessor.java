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
public class AssignmentDtoWithoutProfessor {
	private UUID assignmentId;

	private String title;

	private String description;

	private Integer score;

	private Boolean isActive;

	private LocalDateTime startTime;

	private LocalDateTime endTime;

//	private Student student;

	private ClassroomDtoWithoutProfessor classroom;
}
