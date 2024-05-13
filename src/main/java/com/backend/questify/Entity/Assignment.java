package com.backend.questify.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "assignments")
public class Assignment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long assignmentId;

	@ManyToOne
	@JoinColumn(name = "professor_id")
	private Professor professor; // add later for reusing purpose


	private String title;
	private String description;

	private Integer score;

//	@Column(nullable = true)
//	private LocalDateTime submissionTime;
	private Boolean isActive = false;

	@Column(nullable = true)
	private LocalDateTime startTime;

	@Column(nullable = true)
	private LocalDateTime endTime;
	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;

	@ManyToOne
	@JoinColumn(name = "classroom_id")
	private Classroom classroom;

}
