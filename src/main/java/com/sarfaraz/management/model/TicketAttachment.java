package com.sarfaraz.management.model;

import java.time.LocalDate;

import javax.persistence.*;

@Table(name = "attachment")
public class TicketAttachment {
	@Id
	@GeneratedValue(generator = "id_sec", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "id_sec", sequenceName = "jpa_sequence", allocationSize = 5)
	@Column(nullable = false, precision = 5)
	private long id;
	private String path;
	private String type;
	private String detail;
	private LocalDate added;
	@ManyToOne
	private Ticket issue;

}
