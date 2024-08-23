package com.backend.questify.Entity;

import com.backend.questify.Model.SubmitStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reports")
public class Report {
	@Id
	@GeneratedValue(generator = "UUID")
	private UUID reportId;

	@OneToOne
	@JoinColumn(name = "submission_id", nullable = false)
	private Submission submission;

	@OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Logging> loggings;

	private Integer givenScore;

	private Integer maxScore;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime startTime;

	private LocalDateTime submitTime;

	@Enumerated(EnumType.STRING)
	private SubmitStatus submitStatus;

	@PrePersist
	public void prePersist() {
		this.submitStatus = SubmitStatus.IN_PROGRESS;
		this.startTime = LocalDateTime.now();
		if (this.maxScore == null && this.submission != null) {
			this.maxScore = this.submission.getQuestion().getLaboratory().getMaxScore();
		}
	}

	public void markSubmitted() {


	}
}
