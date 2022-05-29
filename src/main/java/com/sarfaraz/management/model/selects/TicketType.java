package com.sarfaraz.management.model.selects;

public enum TicketType {

	ISSUE("Bug/Issue"), FEATURE("Feature Request"), PERFORMANCE("Performance Issue");

	private String name;

	private TicketType(String name) {
		this.name = name;
	}

	public String getSimpleName() {
		return name;
	}

}
