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
@Table(name = "test_cases")
public class TestCase {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
@Id
@GeneratedValue(generator = "UUID")
//	@GeneratedValue(strategy = GenerationType.AUTO)
@Column(updatable = false, nullable = false)
	private Long testCaseId;
	private String input;
	private String expectedOutput;

	@ManyToOne
	@JoinColumn(name = "laboratory_id")
	private Laboratory laboratory;
}