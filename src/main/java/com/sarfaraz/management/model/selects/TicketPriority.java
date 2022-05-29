package com.sarfaraz.management.model.selects;

public enum TicketPriority {
	LOWEST("Lowest"), LOW("Low"), MEDIUM("Meduim"), HIGH("High"), HIGHEST("Highest");

	private String name;

	TicketPriority(String name) {
		this.name = name;
	}

	public String getSimpleName() {
		return name;
	}
}
