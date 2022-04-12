package com.sarfaraz.management.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sarfaraz.management.exception.TicketNotFoundException;
import com.sarfaraz.management.model.Ticket;
import com.sarfaraz.management.model.dto.TicketListDTO;
import com.sarfaraz.management.service.TicketService;

@RestController
@RequestMapping(value = { "/api/ticket" })
public class TicketController {

	private final Logger log = LoggerFactory.getLogger(TicketController.class);
	private final TicketService service;

	@Autowired
	public TicketController(TicketService service) {
		this.service = service;
	}

	@GetMapping(path = { "/all" })
	public List<TicketListDTO> getAllTickets() {
		List<TicketListDTO> tickets = service.listAll();
		log.info(tickets.toString());
		return tickets;

	}

	@GetMapping(path = { "/all/{id}" })
	public List<TicketListDTO> getAllTicketsRelatedToProject(@PathVariable(name = "id", required = true) Long id) {
		List<TicketListDTO> ts = service.getAllTicketsByProjectId(id);
		ts.forEach(c -> {
			c.getCreated().toString();

		});
		log.info(ts.toString());
		return ts;
	}

	@GetMapping(path = { "/{id}" })
	public TicketListDTO getOneTicket(@PathVariable(name = "id", required = true) Long id) {
		Optional<TicketListDTO> ts = service.getOneTicket(id);
		return ts.orElseThrow(TicketNotFoundException::new);
	}

	@RequestMapping(path = { "/save",
			"/update" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Long>> saveTicket(@RequestBody Ticket ticket) {
		log.info(ticket.toString());
		long savedId = service.save(ticket);
		Map<String, Long> res = Map.of("id", savedId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(path = { "/user/assign" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Boolean>> assignUser(@RequestBody Map<String, Long> json) {
		log.info(json.get("ticket_id") + " :: " + json.get("user_id"));
		boolean result = service.AssignUser(json.get("ticket_id"), json.get("user_id"));
		Map<String, Boolean> res = Map.of("status", result);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(path = { "/user/remove" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Boolean>> removeUser(@RequestBody Map<String, Long> json) {
		log.info(json.get("ticket_id") + " :: " + json.get("user_id"));
		boolean result = service.removeAssignedUser(json.get("ticket_id"));
		Map<String, Boolean> res = Map.of("status", result);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
