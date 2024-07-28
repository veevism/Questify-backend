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

	@OneToMany(mappedBy = "professor")
	private Set<Laboratory> laboratories;


}