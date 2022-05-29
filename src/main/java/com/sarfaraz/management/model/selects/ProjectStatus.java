package com.sarfaraz.management.model.selects;

public enum ProjectStatus {
	ACTIVE("Active"), DEVELOPMENT("Under Development"), COMPLETED("Completed"), HOLD("On Hold"),;

	private final String name;

	ProjectStatus(String name) {
		this.name = name;
	}

	public String getSimpleName() {
		return name;
	}

}