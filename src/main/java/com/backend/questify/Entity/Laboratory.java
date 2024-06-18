package com.backend.questify.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

	@Lob
	@Column(columnDefinition = "TEXT")
	private String problemStatement;

//	@ElementCollection
//	@CollectionTable(name = "laboratory_inputs", joinColumns = @JoinColumn(name = "laboratory_id"))
//	private List<LaboratoryInput> inputs = new ArrayList<>(); // display as a seperate view to indicate the input just that.
//
//	@Lob
//	@Column(columnDefinition = "TEXT")
//	private List<LaboratoryOutput> output;

//	@Lob
//	@Column(columnDefinition = "TEXT")
//	private String sampleInput;
//
//	@Lob
//	@Column(columnDefinition = "TEXT")
//	private String sampleOutput;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "laboratory", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<TestCase> testCases = new ArrayList<>();

	public void addTestCase(TestCase testCase) {
		testCases.add(testCase);
		testCase.setLaboratory(this);
	}

	public void removeTestCase(TestCase testCase) {
		testCases.remove(testCase);
		testCase.setLaboratory(null);
	}
	//mappedby = "problem"
//	@OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<TestCase> testCases = new ArrayList<>();

}