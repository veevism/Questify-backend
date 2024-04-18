package com.backend.questify.Entity;


import jakarta.persistence.*;
import lombok.*;


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
	@MapsId
	@JoinColumn(name = "professor_id")
	private User user;

	private String faculty;
	private String department;

}