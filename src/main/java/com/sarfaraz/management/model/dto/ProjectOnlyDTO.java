package com.sarfaraz.management.model.dto;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sarfaraz.management.model.selects.ProjectStatus;

public interface ProjectOnlyDTO {

	Long getId();

	String getName();

	String getDetail();

	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate getLastDate();

	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate getCreated();

	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate getUpdated();

	@Enumerated(EnumType.STRING)
	ProjectStatus getStatus();

}
