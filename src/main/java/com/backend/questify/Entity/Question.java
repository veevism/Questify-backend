package com.backend.questify.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "questions")
public class Question {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long laboratoryId;
@Id
@GeneratedValue(generator = "UUID")

//	@GeneratedValue(strategy = GenerationType.AUTO)
@Column(updatable = false, nullable = false)
private UUID questionId;

	@ManyToOne
	@JoinColumn(name = "laboratory_id")
	private Laboratory laboratory;

	@ManyToOne
	@JoinColumn(name = "professor_id")
	private Professor professor;

	private String title;

	@Lob
	@Column(columnDefinition = "TEXT")
	private String problemStatement;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<TestCase> testCases = new ArrayList<>();

	public void addTestCase(TestCase testCase) {
		testCases.add(testCase);
		testCase.setQuestion(this);
	}

	public void removeTestCase(TestCase testCase) {
		testCases.remove(testCase);
		testCase.setQuestion(null);
	}

}