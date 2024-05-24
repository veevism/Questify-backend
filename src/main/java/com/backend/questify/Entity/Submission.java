package com.backend.questify.Entity;

import com.backend.questify.Model.SubmissionStatus;
import com.backend.questify.Util.HashMapConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

	@Convert(converter = HashMapConverter.class)
	@Column(columnDefinition = "TEXT")
	@Builder.Default
	private Map<String, String> codeSnippets = new HashMap<>();

	@PrePersist
	public void prePersist() {
		if (this.codeSnippets.isEmpty()) {
			this.codeSnippets = getDefaultSnippets();
		}
	}

// <-- Next Phase

	@OneToMany(mappedBy = "submission", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SubmissionTestCaseResult> testCaseResults;

	@Column(nullable = true)
	private LocalDateTime submissionTime;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	private Integer givenScore;

	private Integer maximumScore;

// Next Phase --->

	@Enumerated(EnumType.STRING)
	private SubmissionStatus status;

	public static Map<String, String> getDefaultSnippets() {
		Map<String, String> snippets = new HashMap<>();
		snippets.put("Java", "public class Main { public static void main(String[] args) { System.out.println(\"Welcome To Questify Ja from Java\"); } }");
		snippets.put("Python", "print('Welcome To Questify Ja from Python')");
		snippets.put("C", "#include <stdio.h>\nint main() { printf(\"Welcome To Questify Ja from C\"); return 0; }");
		snippets.put("JavaScript", "console.log('Welcome To Questify Ja from Javascript');");
		return snippets;
	}


}