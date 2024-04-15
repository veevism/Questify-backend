package com.backend.questify.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "problems")
public class Problem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long problemId;

	@OneToOne
	@JoinColumn(name = "laboratory_id")
	private Laboratory laboratory;

	private String problemStatement;
	private String inputFormat;
	private String outputFormat;
	private String sampleInput;
	private String sampleOutput;

	@OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TestCase> testCases = new ArrayList<>();

}