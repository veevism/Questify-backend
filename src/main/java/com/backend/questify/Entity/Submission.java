package com.backend.questify.Entity;

import com.backend.questify.DTO.TestCaseResultDto;
import com.backend.questify.Model.SubmissionStatus;
import com.backend.questify.Model.SubmitStatus;
import com.backend.questify.Util.HashMapConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "submissions")
public class Submission {
	//Store code only
	@Id
	@GeneratedValue(generator = "UUID")
	@Column(updatable = false, nullable = false)
	private UUID submissionId;

	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question;

	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;

	@ManyToOne
	@JoinColumn(name = "professor_id")
	private Professor professor;

	@Convert(converter = HashMapConverter.class)
	@Column(columnDefinition = "TEXT")
	@Builder.Default
	private Map<String, String> codeSnippets = new HashMap<>();

	private SubmissionStatus status;

	public static Map<String, String> getDefaultSnippets() {
		Map<String, String> snippets = new HashMap<>();
		snippets.put("Java", "public class Main { public static void main(String[] args) { System.out.println(\"Welcome To Questify Ja from Java\"); } }");
		snippets.put("Python", "print('Welcome To Questify Ja from Python')");
		snippets.put("C", "#include <stdio.h>\nint main() { printf(\"Welcome To Questify Ja from C\"); return 0; }");
		snippets.put("JavaScript", "console.log('Welcome To Questify Ja from Javascript');");
		return snippets;
	}

// <-- Next Phase

	@OneToOne(mappedBy = "submission", cascade = CascadeType.ALL, orphanRemoval = true)
	private Report report;

	@Column(updatable = false)
	private LocalDateTime startTime;

	@Column(updatable = false)
	private LocalDateTime endTime;


	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@PrePersist
	public void prePersist() {
		if (this.codeSnippets.isEmpty()) {
			this.codeSnippets = getDefaultSnippets();
		}
		if (this.startTime == null) {
			this.startTime = LocalDateTime.now();
		}
		if (this.question != null && this.question.getLaboratory() != null) {
			Integer durationTime = this.question.getLaboratory().getDurationTime();
			if (durationTime != null) {
				this.endTime = this.startTime.plusSeconds(durationTime);
			}
		}
		if (this.codeSnippets.isEmpty()) {
			this.codeSnippets = getDefaultSnippets();
		}
	}

	@OneToMany(mappedBy = "submission", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<TestCaseResult> testCaseResults = new ArrayList<>();



// Next Phase --->

//	@Enumerated(EnumType.STRING)
//	private SubmissionStatus status; // NEW, SUBMITTED, GRADED

	//	private Integer givenScore;

	// 	@Column(nullable = true)
	//	private LocalDateTime submissionTime;

	// Todo 1 new model using this submission combine with test case result for calculating score
	// Todo 1 WTF already done

	// End Next Phase




}