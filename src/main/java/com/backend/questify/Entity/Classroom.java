package com.backend.questify.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "classrooms")
public class Classroom {
	@Id
	@GeneratedValue(generator = "UUID")
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private UUID classroomId;

	@ManyToOne
	@JoinColumn(name = "professor_id")
	private Professor professor;

	private String title;

	private String description;

	private Boolean isActive = true;

	private String invitationCode;

	@OneToMany(mappedBy = "classroom")
	private Set<Assignment> assignments;

	@ManyToMany
	@JoinTable(
			name = "classroom_student",
			joinColumns = @JoinColumn(name = "classroom_id"),
			inverseJoinColumns = @JoinColumn(name = "student_id")
	)
	private Set<Student> students;

}
