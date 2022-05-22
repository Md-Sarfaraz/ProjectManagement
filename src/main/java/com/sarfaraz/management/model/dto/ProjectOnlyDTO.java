package com.sarfaraz.management.model.dto;

import java.time.LocalDate;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonFormat;

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

	String getStatus();
	
}
