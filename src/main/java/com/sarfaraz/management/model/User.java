package com.sarfaraz.management.model;

import com.fasterxml.jackson.annotation.*;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(generator = "user_id_sec", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "user_id_sec", sequenceName = "users_sequence", allocationSize = 5)
	@Column(nullable = false, precision = 5)
	private Long id;
	@NotBlank
	private String name;
	@NotBlank
	private String email;
	@NotBlank(message = "Username Can Not Be Empty")
	private String username;
	@NotBlank(message = "Password Can Not Be Empty")
	@JsonIgnore
	private String password;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dob;
	private String mobile;
	private String address;
	private boolean active;

//	@JsonIgnore
//	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
//	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
//	private Set<Role> roles = new HashSet<>();

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "id", referencedColumnName = "id"))
	@Column(name = "role")
	private Set<String> roles;

	@ToString.Exclude
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "users_project", joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"))
	private Set<Project> projects = new HashSet<>();

	public User() {
	}

	public User(Long id) {
		this.id = id;
	}

	public User(String name) {
		this.name = name;
	}

	public User(Long id, @NotBlank String name) {
		this.id = id;
		this.name = name;
	}

	public void addRole(String role) {
		this.roles.add(role);
	}

	public void addProject(Project p) {
		this.projects.add(p);
	}

}
