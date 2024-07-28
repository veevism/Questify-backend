package com.backend.questify.DTO;

import com.backend.questify.Entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorDto {
	private Long professorId;
	private String email;
	private String firstName_EN;
	private String displayName;
}
