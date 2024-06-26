package com.backend.questify.DTO;

import com.backend.questify.Model.Status;
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

	private Status status;

	private LocalDateTime startTime;

	private LocalDateTime endTime;

//	private Student student;

}
