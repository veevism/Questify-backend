package com.backend.questify.Entity;

import com.backend.questify.Model.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "laboratories")
public class Laboratory {
	@Id
	@GeneratedValue(generator = "UUID")
	@Column(updatable = false, nullable = false)
	private UUID laboratoryId;

	@ManyToOne
	@JoinColumn(name = "professor_id")
	private Professor professor;

	private String title;

	private String description;

	private String invitationCode;

	private Integer maxScore;

	private Status status; //PUBLISH, DRAFT

	private Integer duration;

	@ManyToMany
	@JoinTable(
			name = "laboratory_student",
			joinColumns = @JoinColumn(name = "laboratory_id"),
			inverseJoinColumns = @JoinColumn(name = "student_id")
	)
	@Builder.Default
	private List<Student> students = new ArrayList<>();

	public void removeStudentFromLaboratory(Student student) {
		students.remove(student);
		student.getLaboratories().remove(this);
	}

	public void addStudentToLaboratory(Student student) {
		students.add(student);
		student.getLaboratories().add(this);
	}

	@ElementCollection
	@CollectionTable(name = "laboratory_student_labs", joinColumns = @JoinColumn(name = "laboratory_id"))
	@MapKeyJoinColumn(name = "student_id")
	@Column(name = "question_id")
	private Map<Long, UUID> studentQuestion = new HashMap<>();

}
