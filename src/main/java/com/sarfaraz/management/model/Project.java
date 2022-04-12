package com.sarfaraz.management.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "projects")
public class Project {

	@Id
	@GeneratedValue(generator = "project_id_sec", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "project_id_sec", sequenceName = "projects_sequence", allocationSize = 5)
	@Column(nullable = false, updatable = false, precision = 5)
	private Long id;
	private String name;
	@Lob
	@Column(columnDefinition = "TEXT")
	private String detail;
	// @DateTimeFormat(pattern = "dd-MM-yyyy")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate lastDate;
	// @DateTimeFormat(pattern = "dd-MM-yyyy")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate created;
	// @DateTimeFormat(pattern = "dd-MM-yyyy")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate updated;
	private String status;
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "projects")
	private Set<User> users = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.REMOVE)
	private Set<Ticket> tickets = new HashSet<>();

	public Project(long id) {
		this.id = id;
	}

	public Project() {
	}

	public Project(Long id, String name) {
		this.name = name;
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getCreated() {
		return created;
	}

	public void setCreated(LocalDate created) {

		this.created = created;
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public LocalDate getLastDate() {
		return lastDate;
	}

	public void setLastDate(LocalDate lastDate) {
		this.lastDate = lastDate;
	}

	public LocalDate getUpdated() {
		return updated;
	}

	public void setUpdated(LocalDate updated) {
		this.updated = updated;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public void addUser(User user) {
		user.addProject(this);
		this.users.add(user);
	}

	@Override
	public String toString() {
		return "Project{" + "id=" + id + ", name='" + name + '\'' + ", detail='" + detail + '\'' + ", lastDate="
				+ lastDate + ", created=" + created + ", updated=" + updated + ", status='" + status + '\'' + "}";
	}
}
