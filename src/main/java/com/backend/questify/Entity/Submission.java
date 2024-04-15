package com.backend.questify.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "submissions")
public class Submission {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long submissionId;

	@ManyToOne
	@JoinColumn(name = "laboratory_id")
	private Laboratory laboratory;

	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;

	@Column(columnDefinition = "JSONB")
	private String codeSnippets;


	@Column(nullable = true)
	private LocalDateTime submissionTime;

	@Column(nullable = true)
	private LocalDateTime startTime;

	@Column(nullable = true)
	private LocalDateTime endTime;
}