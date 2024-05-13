package com.backend.questify.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "laboratories")
public class Laboratory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long laboratoryId;

	@ManyToOne
	@JoinColumn(name = "classroom_id")
	private Classroom classroom;

	@ManyToOne
	@JoinColumn(name = "professor_id")
	private Professor professor; // add later for reusing purpose

	private String labTitle;
	private String description;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	@OneToOne(mappedBy = "laboratory", cascade = CascadeType.ALL)
	private Problem problem;

}