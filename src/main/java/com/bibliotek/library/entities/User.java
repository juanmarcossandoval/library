package com.bibliotek.library.entities;

import java.util.Set;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@NotEmpty
	@NotNull
	@Length(max = 50)
	@Column(nullable = false, unique = true, length = 50)
	private String username;
	
	@Email
	@NotNull
	@Column(nullable = false, unique = true)
	private String mail;
	
	@NotBlank
	@NotEmpty
	@NotNull
	@Length(min = 8, max = 50)
	@Column(nullable = false, length = 50)
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class, cascade = CascadeType.PERSIST)
	@JoinTable(name = "users_roles", 
		joinColumns = @JoinColumn(name = "fk_user"),
		inverseJoinColumns = @JoinColumn(name = "fk_role"))
	private Set<Role> roles;
}
