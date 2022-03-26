package com.sarfaraz.management.model;

import com.fasterxml.jackson.annotation.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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
	private String password;
	// @DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dob;
	private String mobile;
	private String address;
	private boolean active;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<>();

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "users_project", joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"))
	private Set<Project> projects = new HashSet<>();

	public User(Long id) {
		this.id = id;
	}

	public User() {
	}

	public User(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> role) {
		this.roles = role;
	}

	public void addRole(Role role) {
		if (this.roles.contains(role))
			return;
		this.roles.add(role);
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	public void addProject(Project p) {
		this.projects.add(p);
	}

	@Override
	public String toString() {
		return "User{" + "id=" + id + ", name='" + name + '\'' + ", email='" + email + '\'' + ", username='" + username
				+ '\'' + ", password='" + password + '\'' + ", dob=" + dob + ", mobile='" + mobile + '\''
				+ ", address='" + address + '\'' +

				'}';
	}
}
