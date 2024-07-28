package com.backend.questify.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "submission_test_case_results")
public class SubmissionTestCaseResult {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "submission_id")
	private Submission submission;

	@ManyToOne
	@JoinColumn(name = "test_case_id")
	private TestCase testCase;

	@Column
	private Boolean passed; // Change to
}
