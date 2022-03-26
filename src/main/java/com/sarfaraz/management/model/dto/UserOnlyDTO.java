package com.sarfaraz.management.model.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface UserOnlyDTO {

	Long getId();

	String getName();

	String getEmail();

	String getUsername();

	String getPassword();

	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate getDob();

	String getMobile();

	String getAddress();

	boolean getActive();

}
