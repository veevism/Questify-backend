package com.backend.questify.DTO;

import com.backend.questify.Entity.Assignment;
import com.backend.questify.Entity.Classroom;
import com.backend.questify.Entity.Problem;
import com.backend.questify.Entity.Professor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LaboratoryDto {

	private Assignment assignment;
	private String labTitle;
	private String description;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Problem problem;
}
