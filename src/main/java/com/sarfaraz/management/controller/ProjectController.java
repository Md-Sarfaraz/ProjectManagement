package com.sarfaraz.management.controller;

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
import com.sarfaraz.management.model.User;
import com.sarfaraz.management.model.dto.ProjectOnlyDTO;
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

	@GetMapping(value = { "/list", "/all" })
	public ResponseEntity<ResponsePageable> getSortedPageable(
			final @RequestParam(value = "page", required = false, defaultValue = "1") int page,
			final @RequestParam(value = "size", required = false, defaultValue = "20") int size,
			final @RequestParam(value = "sort", required = false, defaultValue = "name") String sort)
			throws JSONException {
		Page<ProjectOnlyDTO> projects = service.sortedByField(page, size, sort);

		ResponsePageable response = new ResponsePageable(projects.getTotalPages(), projects.getTotalElements(),
				projects.getSize(), projects.getNumber() + 1, projects.toList());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = { "/save", "/update" }, method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Map<String, Long>> addProject(@RequestBody Project project, HttpServletRequest request) {
		long savedId = service.save(project);
		log.info(project.toString());
		Map<String, Long> res = Map.of("id", savedId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@CrossOrigin
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

	@GetMapping(path = { "/search" })
	public ResponseEntity<ResponsePageable> searchProjectByField(final @RequestParam("name") Optional<String> name,
			final @RequestParam(value = "page", required = false, defaultValue = "1") int p,
			final @RequestParam(value = "size", required = false, defaultValue = "10") int size,
			final @RequestParam(value = "sort", required = false, defaultValue = "name") String sort) {

		Page<ProjectOnlyDTO> page = service.searchByField(name.get(), p, size, sort);

		log.info(page.toList().toString());

		ResponsePageable response = new ResponsePageable(page.getTotalPages(), page.getTotalElements(), page.getSize(),
				page.getNumber() + 1, page.toList());
		return new ResponseEntity<>(response, HttpStatus.OK);
//		Map<String, String> user = Map.of("name", name.orElse("Null"), "email", email.orElse("Null"));
		// return users;
	}

//	@GetMapping({ "/view" })
//	public Set<NameAndRole> getOneProjet(@RequestParam("id") Long id) {
//		return service.getAllRelatedUsers(id);
//	}

	/*
	 * @GetMapping({"/detail/tickets"}) public List<TicketListProjectDTO>
	 * getTickets(@RequestParam("id") Long id) {
	 * log.info("return from detail/tickets"); return
	 * ticketService.getAllTicketsRelatedToProject(id); }
	 */

	@GetMapping({ "/users/all", "/users", "/users/list" })
	public Set<User> listAllUsers(@RequestParam Long pid) {
		Set<User> users = service.getAllUserByProjectID(pid);
		return users;
	}

	@CrossOrigin
	@RequestMapping(value = { "/users/add" }, method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> addUsertoProject(@RequestBody Map<String, Long> json) {
		service.addUserToProject(json.get("pid"), json.get("uid"));
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping({ "/users/delete" })
	public ResponseEntity<String> deleteUserFromProject(@RequestBody Map<String, Long> json) {
		service.removeUserFromProject(json.get("pid"), json.get("uid"));
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
