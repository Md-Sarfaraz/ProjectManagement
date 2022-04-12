package com.sarfaraz.management.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sarfaraz.management.config.LocalDateAttributeConverter;

@Entity
@Table(name = "tickets")
public class Ticket implements Serializable {

	@Id
	@GeneratedValue(generator = "id_sec", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "id_sec", sequenceName = "ticket_sequence", allocationSize = 5)
	@Column(nullable = false, precision = 5)
	private Long id;
	private String name;
	private String detail;
	private String type;
	private String status;
	private String priority;
	// @Convert(converter = LocalDateAttributeConverter.class)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate created;
	@JsonFormat(pattern = "yyyy-MM-dd")
	// @Convert(converter = LocalDateAttributeConverter.class)
	private LocalDate updated;
	@JsonFormat(pattern = "yyyy-MM-dd")
	// @Convert(converter = LocalDateAttributeConverter.class)
	private LocalDate lastDate;

	@ManyToOne
	@JoinColumn(name = "fk_project")
	private Project project;

	@ManyToOne
	@JoinColumn(name = "fk_assigneduser")
	private User assignedUser;

	@ManyToOne
	@JoinColumn(name = "fk_submitter")
	private User submitter;

	public Ticket() {
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

	public LocalDate getUpdated() {
		return updated;
	}

	public void setUpdated(LocalDate updated) {
		this.updated = updated;
	}

	public LocalDate getLastDate() {
		return lastDate;
	}

	public void setLastDate(LocalDate lastDate) {
		this.lastDate = lastDate;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public User getUser() {
		return assignedUser;
	}

	public void setUser(User assignedUser) {
		this.assignedUser = assignedUser;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public User getAssignedUser() {
		return assignedUser;
	}

	public void setAssignedUser(User assignedUser) {
		this.assignedUser = assignedUser;
	}

	public User getSubmitter() {
		return submitter;
	}

	public void setSubmitter(User submitter) {
		this.submitter = submitter;
	}

	@Override
	public String toString() {

		return "Ticket{" + "id=" + id + ", name='" + name + '\'' + ", detail='" + detail + '\'' + ", status='" + status
				+ '\'' + ", priority='" + priority + '\'' + ", created=" + created + ", updated=" + updated
				+ ", lastDate=" + lastDate + ", project=" + project.getId() + " : " + project.getName() + ", type="
				+ type + ", assignedUser=" + assignedUser + ", submitter=" + submitter.getId() + " : "
				+ submitter.getName() + '}';
	}
}
