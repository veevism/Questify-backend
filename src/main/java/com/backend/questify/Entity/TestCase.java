package com.backend.questify.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "test_cases")
public class TestCase {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)

//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@GeneratedValue(generator = "UUID")
	@Column(updatable = false, nullable = false)
	private UUID testCaseId;
	@Column(columnDefinition = "TEXT")
	private String input;
	@Column(columnDefinition = "TEXT")
	private String expectedOutput;

	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question;
}