package com.backend.questify.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
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

	private Boolean isActive = false;

	private String invitationCode;

	@OneToMany(mappedBy = "classroom")
	private Set<Assignment> assignments;

}
