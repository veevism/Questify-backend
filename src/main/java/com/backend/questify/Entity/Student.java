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
@Table(name = "students")
public class Student {
	@Id
	private Long studentId;

	@OneToOne
	@MapsId
	@JoinColumn(name = "student_id")
	private User user;

	private Integer enrollmentYear;
	private String major;

}