package com.backend.questify.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
public class Student {
	@Id
	private Long studentId;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "student_id")
	@JsonBackReference
	private User user;

	private Integer enrollmentYear;
	private String major;

	@ManyToMany(mappedBy = "students")
	@Builder.Default
	private List<Classroom> classrooms = new ArrayList<>();

}