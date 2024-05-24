package com.backend.questify.Entity;

import com.backend.questify.Model.GradingCriteria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "assignments")
public class Assignment {
	@Id
	@GeneratedValue(generator = "UUID")
	@Column(updatable = false, nullable = false)
	private UUID assignmentId;

	@ManyToOne
	@JoinColumn(name = "professor_id")
	private Professor professor; // add later for reusing purpose

	private String title;
	private String description;

	private Integer score;

//	@Column(nullable = true)
//	private LocalDateTime submissionTime;
	private Boolean isActive = true;

	@Column(nullable = true)
	private LocalDateTime startTime;

	@Column(nullable = true)
	private LocalDateTime endTime;


	@Column(nullable = true) // change to false later
	@Enumerated(EnumType.STRING)
	private GradingCriteria gradingCriteria; // didn't add to the diagram
//	@ManyToOne
//	@JoinColumn(name = "student_id")
//	private Student student;

	@ManyToOne
	@JoinColumn(name = "classroom_id")
	private Classroom classroom;

}
