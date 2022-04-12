package com.sarfaraz.management.model.dto;

import org.springframework.beans.factory.annotation.Value;

public interface TicketListDTO {
	Long getId();

	String getName();

	String getDetail();

	// @JsonFormat(pattern = "yyyy-MM-dd")
	// @Convert(converter = LocalDateAttributeConverter.class)
	@Value("#{target.created.toString()}")
	String getCreated();

	// @JsonFormat(pattern = "yyyy-MM-dd")
	// @Convert(converter = LocalDateAttributeConverter.class)
	@Value("#{target.updated.toString()}")
	String getUpdated();

	// @JsonFormat(pattern = "yyyy-MM-dd")
	// @Convert(converter = LocalDateAttributeConverter.class)
	@Value("#{target.lastDate.toString()}")
	String getLasDate();

	String getStatus();

	String getPriority();

	String getType();

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
