package com.backend.questify.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "professors")
public class Professor {
	@Id
	private Long professorId;

	@OneToOne
	@MapsId
	@JoinColumn(name = "professor_id")
	private User user;

	private String faculty;
	private String department;

}