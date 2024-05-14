package com.backend.questify.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "professors")
public class Professor {
	@Id
	@EqualsAndHashCode.Exclude
	private Long professorId;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId // This is very suspicious
	@JoinColumn(name = "professor_id")
	private User user;

	private String faculty;
	private String department;


	@OneToMany(mappedBy = "professor")
	private Set<Classroom> classrooms;

	@OneToMany(mappedBy = "professor")
	private Set<Assignment> assignments;

	@OneToMany(mappedBy = "professor")
	private Set<Laboratory> laboratories;

	public void addAssignment(Assignment assignment) {
		assignments.add(assignment);
//		assignment.setProfessor(this);
	}

	public void removeAssignment(Assignment assignment) {
		assignments.remove(assignment);
		assignment.setProfessor(null);
	}


}