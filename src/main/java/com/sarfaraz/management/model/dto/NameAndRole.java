package com.sarfaraz.management.model.dto;

import java.util.HashSet;
import java.util.Set;

public class NameAndRole {

	private Long id;
	private String name;
	private String email;
	private Set<RoleName> roles = new HashSet<>();

	public NameAndRole(Long id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}


	class RoleName {
		public RoleName(Long rId, String rolename) {
			// TODO Auto-generated constructor stub
		}

		private Long id;
		private String name;

	}

	public NameAndRole(Long id, String name, String email, Long rId, String rolename) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.roles.add(new RoleName(rId, rolename));
	}

	public void addRole(Set<RoleName> role) {
		this.roles.addAll(role);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		NameAndRole that = (NameAndRole) o;
		if (!id.equals(that.id))
			return false;
		that.addRole(this.roles);
		return email != null ? email.equals(that.email) : that.email == null;
	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		return result;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<RoleName> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleName> roles) {
		this.roles = roles;
	}

	public NameAndRole(Long id, String name, String email, Set<RoleName> roles) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.roles = roles;
	}

	public NameAndRole() {
		super();
	}

}
