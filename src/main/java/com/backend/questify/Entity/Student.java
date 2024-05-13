package com.backend.questify.Entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
public class Student {
	@Id
	@EqualsAndHashCode.Exclude
	private Long studentId;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "student_id")
	private User user;

	private Integer enrollmentYear;

	private String major;

}