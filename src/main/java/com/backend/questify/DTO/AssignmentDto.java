package com.backend.questify.DTO;

import com.backend.questify.Entity.Classroom;
import com.backend.questify.Entity.Professor;
import com.backend.questify.Entity.Student;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class AssignmentDto {
	private Long assignmentId;

	private Professor professor;


	private String title;
	private String description;

	private Integer score;

	private Boolean isActive;

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	private Student student;


	private Classroom classroom;

}
