package com.sarfaraz.management.model.selects;

public enum TicketStatus {
	OPEN("Open"), CLOSE("Close"), HOLD("Hold"), REJECTED("Rejected");

	private String name;

	TicketStatus(String name) {
		this.name = name;
	}

	public String getSimpleName() {
		return name;
	}
}