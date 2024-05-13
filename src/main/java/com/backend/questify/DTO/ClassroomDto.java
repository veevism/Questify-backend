package com.backend.questify.DTO;

import com.backend.questify.Entity.Assignment;
import com.backend.questify.Entity.Professor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
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

	private Boolean isActive;

	private String invitationCode;

	private Set<Assignment> assignments;
}
