package com.sarfaraz.management.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sarfaraz.management.model.selects.UserRoles;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
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

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "id", referencedColumnName = "id"))
	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private Set<UserRoles> roles;

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

	public void addRole(UserRoles role) {
		this.roles.add(role);
	}

	public void removeRole(UserRoles role) {
		this.roles.remove(role);
	}

	public void removeRole(String role) {
		this.roles.remove(UserRoles.valueOf(role));
	}

	public void addProject(Project p) {
		this.projects.add(p);

	}

}
