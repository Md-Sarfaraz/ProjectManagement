package com.sarfaraz.management.model.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.beans.factory.annotation.Value;

import com.sarfaraz.management.model.selects.TicketPriority;
import com.sarfaraz.management.model.selects.TicketStatus;
import com.sarfaraz.management.model.selects.TicketType;

public interface TicketListDTO {
	Long getId();

	String getName();

	String getDetail();

	@Value("#{target.created.toString()}")
	String getCreated();

	@Value("#{target.updated.toString()}")
	String getUpdated();

	@Value("#{target.lastDate.toString()}")
	String getLastDate();

	@Enumerated(EnumType.STRING)
	TicketStatus getStatus();

	@Enumerated(EnumType.STRING)
	TicketPriority getPriority();

	@Enumerated(EnumType.STRING)
	TicketType getType();

	ProjectDTO getProject();

	UserDTO getAssignedUser();

	UserDTO getSubmitter();

	interface ProjectDTO {
		Long getId();

		String getName();
	}

	interface UserDTO {
		Long getId();

		String getName();
	}

}
