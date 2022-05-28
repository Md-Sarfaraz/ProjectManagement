package com.sarfaraz.management.model.selects;

public enum UserRoles {
	ROLE_ADMIN("Admin", "Admin can access to all EndPoints"),
	ROLE_MANAGER("Manager", "Manager can manage users, projects and tickets"),
	ROLE_TESTER("Tester",
			"Tester can raise a bug or request for features, access all the tickets of projects which is issued by manager"),
	ROLE_DEVELOPER("Developer", "Developer can accept and deal with tickets of the issued projects"),
	ROLE_PUBLIC("Public", "Public can Only view projects and tickets but can't interact with databases");

	private String name;
	private String desc;

	UserRoles(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}

	public String getDetails() {
		return this.desc;
	}

	public String getSimpleName() {
		return this.name;
	}
}
