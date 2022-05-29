package com.sarfaraz.management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.sarfaraz.management.model.selects.ProjectStatus;
import com.sarfaraz.management.model.selects.TicketPriority;
import com.sarfaraz.management.model.selects.TicketStatus;
import com.sarfaraz.management.model.selects.TicketType;
import com.sarfaraz.management.model.selects.UserRoles;

public class DataInfoService {

	public static List<Map<String, Object>> getUserRoles() {
		List<Map<String, Object>> roles = new ArrayList<>();
		Stream.of(UserRoles.values()).forEachOrdered(
				r -> roles.add(Map.of("value", r.toString(), "simpleName", r.getSimpleName(), "note", r.getDetails())));
		return roles;
	}

	public static List<Map<String, Object>> getProjectStatus() {
		List<Map<String, Object>> roles = new ArrayList<>();
		Stream.of(ProjectStatus.values())
				.forEachOrdered(r -> roles.add(Map.of("value", r.toString(), "simpleName", r.getSimpleName())));
		return roles;
	}

	public static List<Map<String, Object>> getTicketPriority() {
		List<Map<String, Object>> roles = new ArrayList<>();
		Stream.of(TicketPriority.values())
				.forEachOrdered(r -> roles.add(Map.of("value", r.toString(), "simpleName", r.getSimpleName())));
		return roles;
	}

	public static List<Map<String, Object>> getTicketStatus() {
		List<Map<String, Object>> roles = new ArrayList<>();
		Stream.of(TicketStatus.values())
				.forEachOrdered(r -> roles.add(Map.of("value", r.toString(), "simpleName", r.getSimpleName())));
		return roles;
	}

	public static List<Map<String, Object>> getTicketType() {
		List<Map<String, Object>> roles = new ArrayList<>();
		Stream.of(TicketType.values())
				.forEachOrdered(r -> roles.add(Map.of("value", r.toString(), "simpleName", r.getSimpleName())));
		return roles;
	}

	
}
