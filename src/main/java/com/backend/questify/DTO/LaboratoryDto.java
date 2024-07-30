package com.backend.questify.DTO;

import com.backend.questify.Entity.Student;
import com.backend.questify.Model.Status;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LaboratoryDto {

	private UUID laboratoryId;

	private ProfessorDto professor;

	private String title;

	private String description;

	private String invitationCode;

	private Integer maxScore;

	private Status status; //PUBLISH, DRAFT

	private Integer durationTime;

	private List<StudentDto> students = new ArrayList<>();

	private int studentQuantity;

}
