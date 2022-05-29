package com.sarfaraz.management.model.dto;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sarfaraz.management.model.selects.UserRoles;

public interface UserOnlyDTO {

	Long getId();

	String getName();

	String getEmail();

	String getUsername();

	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate getDob();

	String getMobile();

	String getAddress();

	@Enumerated(EnumType.STRING)
	Set<UserRoles> getRoles();

}
