package com.sarfaraz.management.controller;

import java.util.Map;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sarfaraz.management.exception.UserNotFoundException;
import com.sarfaraz.management.model.Project;
import com.sarfaraz.management.model.ResponseData;
import com.sarfaraz.management.model.dto.NameAndRole;
import com.sarfaraz.management.model.dto.ProjectOnlyDTO;
import com.sarfaraz.management.service.ProjectService;

@RestController
@RequestMapping(value = { "/api/project" })
public class ProjectController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final ProjectService service;

	@Autowired
	public ProjectController(ProjectService service) {
		this.service = service;
	}

	@GetMapping(value = { "/list", "", "/", "/all" })
	public ResponseEntity<ResponseData> getSortedPageable(
			final @RequestParam(value = "page", required = false, defaultValue = "1") int page,
			final @RequestParam(value = "size", required = false, defaultValue = "20") int size,
			final @RequestParam(value = "sort", required = false, defaultValue = "name") String sort)
			throws JSONException {
		Page<ProjectOnlyDTO> projects = service.sortedByfield(page, size, sort);

		ResponseData response = new ResponseData(projects.getTotalPages(), projects.getTotalElements(),
				projects.getSize(), projects.getNumber() + 1, projects.toList());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = { "/save", "/update" }, method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Map<String, Long>> addProject(@RequestBody Project project, HttpServletRequest request){
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

	@GetMapping({ "/detail/users" })
	public Set<NameAndRole> getMembers(@RequestParam("id") Long id) {
		return service.getAllRelatedUsers(id);
	}

	/*
	 * @GetMapping({"/detail/tickets"}) public List<TicketListProjectDTO>
	 * getTickets(@RequestParam("id") Long id) {
	 * log.info("return from detail/tickets"); return
	 * ticketService.getAllTicketsRelatedToProject(id); }
	 */

	@PostMapping({ "/user/add" })
	public ResponseEntity<String> addUsertoProject(@RequestParam("pid") Long pid, @RequestParam("uid") Long uid) {
		service.addUserToProject(pid, uid);
		log.info(pid + " : " + uid);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping({ "/user/delete" })
	public ResponseEntity<String> deleteUserFromProject(@RequestParam("pid") Long pid, @RequestParam("uid") Long uid) {
		service.removeUserFromProject(pid, uid);
		log.info("In Delete :: pid : " + pid + ", Uid : " + uid);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
