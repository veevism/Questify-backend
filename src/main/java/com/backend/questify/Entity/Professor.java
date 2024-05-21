package com.backend.questify.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
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
	private Long professorId;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "professor_id")
	@JsonBackReference
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