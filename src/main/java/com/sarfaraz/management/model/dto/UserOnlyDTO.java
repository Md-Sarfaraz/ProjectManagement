package com.sarfaraz.management.model.dto;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface UserOnlyDTO {

	Long getId();

	String getName();

	String getEmail();

	String getUsername();

	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate getDob();

	String getMobile();

	String getAddress();

	Set<String> getRoles();

}
