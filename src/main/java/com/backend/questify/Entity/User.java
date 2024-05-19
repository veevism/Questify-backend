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
	private String firstName;

	@Column
	private String lastName;

	@Column(nullable = false, unique = false)
	private String displayName;

	@Column(nullable = false)
	private String password;

	@Column
	private String image;

	@Column(nullable = false, unique = true)
	private String email;

	@Enumerated(EnumType.STRING)
	private Role role;

	@OneToOne(fetch = FetchType.LAZY)
	private Student student;

	@OneToOne(fetch = FetchType.LAZY)
	private Professor professor;

}

