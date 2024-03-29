package com.sarfaraz.management.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sarfaraz.management.exception.TicketNotFoundException;
import com.sarfaraz.management.model.ResponsePageable;
import com.sarfaraz.management.model.Ticket;
import com.sarfaraz.management.model.dto.TicketListDTO;
import com.sarfaraz.management.service.TicketService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = { "/api/ticket" })
public class TicketController {

	private final TicketService service;

	@GetMapping(path = { "/list" })
	public ResponseEntity<ResponsePageable> getAllTickets(
			final @RequestParam(value = "project", required = false) Long pid,
			final @RequestParam(value = "page", required = false, defaultValue = "1") int page,
			final @RequestParam(value = "size", required = false, defaultValue = "20") int size,
			final @RequestParam(value = "sort", required = false, defaultValue = "name") String sort,
			final @RequestParam(value = "asc", required = false, defaultValue = "true") boolean acs) {
		if (pid == null) {
			Page<TicketListDTO> tickets = service.listAll(page, size);
			log.info(String.valueOf(tickets.getTotalElements()));
			ResponsePageable response = new ResponsePageable(tickets.getTotalPages(), tickets.getTotalElements(),
					tickets.getSize(), tickets.getNumber() + 1, tickets.toList());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			log.info(pid.toString());
			Page<TicketListDTO> ts = service.getAllTicketsByProjectId(pid, page, size);
			ts.forEach(c -> {
				c.getCreated().toString();
			});
			log.info(ts.toString());
			ResponsePageable response = new ResponsePageable(ts.getTotalPages(), ts.getTotalElements(), ts.getSize(),
					ts.getNumber() + 1, ts.toList());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

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

	@CrossOrigin
	@RequestMapping(path = { "/delete" }, method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteTicket(@RequestParam(name = "id") Long tid) {
		service.delete(tid);
		log.info("Ticket Deleted With Id : {}", tid);
		return ResponseEntity.ok().build();
	}

	@RequestMapping(path = { "/user/assign" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Boolean>> assignUser(@RequestBody Map<String, Long> json) {
		log.info(json.get("ticketId") + " :: " + json.get("userId"));
		boolean result = service.AssignUser(json.get("ticketId"), json.get("userId"));
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
