package com.backend.questify.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "enrollments")
public class Enrollment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long enrollmentId;

	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;

	@ManyToOne
	@JoinColumn(name = "classroom_id")
	private Classroom classroom;

}
