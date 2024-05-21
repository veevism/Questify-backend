package com.backend.questify.Entity;

import com.backend.questify.Model.Role;
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
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(nullable = false, unique = true)
	private String userName;

	@Column
	private String firstName_EN;

	@Column
	private String lastName_EN;

	@Column(nullable = false, unique = false)
	private String displayName;

	@Column
	private String organization_name_EN;

	@Column
	private String image;

	@Column(nullable = false, unique = true)
	private String email;

	@Enumerated(EnumType.STRING)
	private Role role; //StdAcc , ProfAcc

	@OneToOne(fetch = FetchType.LAZY)
	private Student student;

	@OneToOne(fetch = FetchType.LAZY)
	private Professor professor;

}

