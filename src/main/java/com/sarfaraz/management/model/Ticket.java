package com.sarfaraz.management.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sarfaraz.management.model.selects.TicketPriority;
import com.sarfaraz.management.model.selects.TicketStatus;
import com.sarfaraz.management.model.selects.TicketType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tickets")
public class Ticket {

	@Id
	@GeneratedValue(generator = "id_sec", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "id_sec", sequenceName = "ticket_sequence", allocationSize = 5)
	@Column(nullable = false, precision = 5)
	private Long id;
	private String name;
	private String detail;

	@Enumerated(EnumType.STRING)
	private TicketType type;

	@Enumerated(EnumType.STRING)
	private TicketStatus status;

	@Enumerated(EnumType.STRING)
	private TicketPriority priority;

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

}
