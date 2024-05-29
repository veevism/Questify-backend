package com.backend.questify.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

	private String invitationCode;

//	@OneToMany(mappedBy = "classroom")
//	private Set<Assignment> assignments;

	@ManyToMany
	@JoinTable(
			name = "classroom_student",
			joinColumns = @JoinColumn(name = "classroom_id"),
			inverseJoinColumns = @JoinColumn(name = "student_id")
	)
	@Builder.Default
	private List<Student> students = new ArrayList<>();


	public void addStudent(Student student) {
		students.add(student);
		student.getClassrooms().add(this);
	}

	public void removeStudent(Student student) {
		students.remove(student);
		student.getClassrooms().remove(this);
	}

}
