package com.sarfaraz.management.model.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface UserAllInfo {

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

	Set<Project> getProjects();

	Set<String> getRoles();

	interface Project {
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

}
