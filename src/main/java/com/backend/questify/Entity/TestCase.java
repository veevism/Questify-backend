package com.backend.questify.Entity;

import jakarta.persistence.*;


@Entity
@Table(name = "test_cases")
public class TestCase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long testCaseId;

	@ManyToOne
	@JoinColumn(name = "problem_id")
	private Problem problem;

	private String input;
	private String expectedOutput;

}