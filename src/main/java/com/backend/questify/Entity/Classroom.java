package com.backend.questify.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "classrooms")
public class Classroom {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long classroomId;

	@ManyToOne
	@JoinColumn(name = "professor_id")
	private Professor professor;

	private String title;
	private String description;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	@OneToMany(mappedBy = "classroom")
	private Set<Activity> activities;

}
