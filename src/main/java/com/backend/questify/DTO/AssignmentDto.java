package com.backend.questify.DTO;

import com.backend.questify.Entity.Classroom;
import com.backend.questify.Entity.Professor;
import com.backend.questify.Entity.Student;
import jakarta.persistence.*;
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
public class AssignmentDto {
	private UUID assignmentId;

	private ProfessorDto professor;

	private String title;

	private String description;

	private Integer score;

	private Boolean isActive;

	private LocalDateTime startTime;

	private LocalDateTime endTime;
//	private Student student;
	private ClassroomDtoWithoutProfessor classroom;

}
