package com.backend.questify.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "laboratories")
public class Laboratory {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long laboratoryId;
@Id
@GeneratedValue(generator = "UUID")
//	@GeneratedValue(strategy = GenerationType.AUTO)
@Column(updatable = false, nullable = false)
private UUID laboratoryId;

	@ManyToOne
	@JoinColumn(name = "assignment_id")
	private Assignment assignment;

	@ManyToOne
	@JoinColumn(name = "professor_id")
	private Professor professor; // add later for reusing purpose

	private String labTitle;
	private String description;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@OneToOne(mappedBy = "laboratory", cascade = CascadeType.ALL)
	private Problem problem;

}