package com.sarfaraz.management.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sarfaraz.management.exception.UserNotFoundException;
import com.sarfaraz.management.model.Project;
import com.sarfaraz.management.model.ResponsePageable;
import com.sarfaraz.management.model.Ticket;
import com.sarfaraz.management.model.User;
import com.sarfaraz.management.model.dto.ProjectOnlyDTO;
import com.sarfaraz.management.model.dto.UserOnlyDTO;
import com.sarfaraz.management.service.ProjectService;
import com.sarfaraz.management.service.UserService;

@RestController
@RequestMapping(value = { "/api/project" })
public class ProjectController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final ProjectService service;
	private final UserService userService;

	@Autowired
	public ProjectController(ProjectService service, UserService userService) {
		this.service = service;
		this.userService = userService;
	}

	@GetMapping(value = { "/list" })
	public ResponseEntity<ResponsePageable> getSortedPageable(
			final @RequestParam(value = "p", required = false, defaultValue = "1") int page,
			final @RequestParam(value = "s", required = false, defaultValue = "20") int size,
			final @RequestParam(value = "sort", required = false, defaultValue = "name") String sort,
			final @RequestParam(value = "asc", required = false, defaultValue = "true") boolean order)
			throws JSONException {
		Page<ProjectOnlyDTO> projects = service.listAllSorted(page, size, sort, order);

		ResponsePageable response = new ResponsePageable(projects.getTotalPages(), projects.getTotalElements(),
				projects.getSize(), projects.getNumber() + 1, projects.toList());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(path = { "/search" })
	public ResponseEntity<ResponsePageable> searchProjectByField(final @RequestParam("q") Optional<String> name,
			final @RequestParam(value = "p", required = false, defaultValue = "1") int p,
			final @RequestParam(value = "s", required = false, defaultValue = "10") int size,
			final @RequestParam(value = "asc", required = false, defaultValue = "true") boolean order,
			final @RequestParam(value = "sort", required = false, defaultValue = "name") String sort) {

		Page<ProjectOnlyDTO> page = service.search(name.get(), p, size, sort, true);
		log.info(page.toList().toString());
		ResponsePageable response = new ResponsePageable(page.getTotalPages(), page.getTotalElements(), page.getSize(),
				page.getNumber() + 1, page.toList());
		return new ResponseEntity<>(response, HttpStatus.OK);
//		Map<String, String> user = Map.of("name", name.orElse("Null"), "email", email.orElse("Null"));
		// return users;
	}

	@RequestMapping(value = { "/save", "/update" }, method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity addProject(@RequestBody Project project, HttpServletRequest request) {
		boolean savedId = service.save(project);
		log.info(project.toString());
		return ResponseEntity.ok(Map.of("saved", savedId));
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseEntity<Map<String, Boolean>> deleteProject(@RequestParam("id") long pid) {
		boolean status = false;
		try {
			service.delete(pid);
			status = true;
		} catch (Exception e) {
			throw new UserNotFoundException("Can't find any Project with ID : " + pid, e.getCause());
		}
		return new ResponseEntity<>(Map.of("status", status), HttpStatus.OK);
	}

	@GetMapping({ "/tickets" })
	public List<Ticket> getTickets(@RequestParam("id") Long id) {
		log.info("return from detail/tickets");
//		return ticketService.getAllTicketsRelatedToProject(id);
		return null;
	}

	@GetMapping({ "/users/list" })
	public Set<UserOnlyDTO> listRelatedUser(@RequestParam("id") Long pid) {
		Set<UserOnlyDTO> users = userService.getAllbyProjectId(pid);
		log.info("Related User {}", users.size());
		return users;
	}

	@CrossOrigin
	@RequestMapping(value = { "/users/add" }, method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Map> addUsertoProject(@RequestBody Map<String, Long> json) {
		boolean status = service.addUserToProject(json.get("pid"), json.get("uid"));
		return ResponseEntity.ok(Map.of("added", status));
	}

	@PostMapping({ "/users/delete" })
	public ResponseEntity<String> deleteUserFromProject(@RequestBody Map<String, Long> json) {
		service.removeUserFromProject(json.get("pid"), json.get("uid"));
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
