package com.sarfaraz.management.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.sarfaraz.management.model.Ticket;
import com.sarfaraz.management.model.dto.TicketListDTO;
import com.sarfaraz.management.model.dto.TicketListProjectDTO;
import com.sarfaraz.management.repository.TicketRepo;
import com.sarfaraz.management.repository.TicketRepo.Types;
import com.sarfaraz.management.repository.TicketRepo.Priority;
import com.sarfaraz.management.repository.TicketRepo.Status;

@Service
public class TicketService {

	private final TicketRepo repo;
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public TicketService(TicketRepo repo) {
		this.repo = repo;

	}

	@Transactional
	public Long save(Ticket ticket) {
		ticket.setUpdated(LocalDate.now());
		if (ticket.getId() == null) {
			ticket.setCreated(LocalDate.now());
		}
		if (ticket.getProject().getId() == null)
			return -1L;
		if (ticket.getSubmitter() == null)
			return -1L;
		if (ticket.getType() != null) {
			for (Types ty : Types.values()) {
				if (ty.name().equalsIgnoreCase(ticket.getType()))
					ticket.setType(ty.name());
			}
		} else
			ticket.setType(Types.ISSUE.name());

		if (ticket.getStatus() != null) {
			for (Status ty : Status.values()) {
				if (ty.name().equalsIgnoreCase(ticket.getStatus()))
					ticket.setStatus(ty.name());
			}
		} else
			ticket.setStatus(Status.HOLD.name());
		if (ticket.getPriority() != null) {
			for (Priority ty : Priority.values()) {
				if (ty.name().equalsIgnoreCase(ticket.getPriority()))
					ticket.setPriority(ty.name());
			}
		} else
			ticket.setPriority(Priority.MEDIUM.name());

		log.warn(ticket.toString());
		Ticket t = repo.save(ticket);

		return t.getId();
	}

	@Transactional
	public List<TicketListDTO> listAll() {
		return repo.getAllTickets();
	}

	public List<TicketListDTO> getAllTicketsByProjectId(Long id) {
		List<TicketListDTO> tickets = repo.getAllByProjectId(id);
		log.info(tickets.toString());
		return tickets;
	}

	public Optional<TicketListDTO> getOneTicket(Long id) {
		return repo.findOne(id);
	}

	public void delete(Long id) {
		repo.deleteById(id);
	}

	@Transactional
	public boolean AssignUser(Long ticketId, Long UserId) {
		int result = repo.assignNewUser(ticketId, UserId);
		log.info("Result : " + result);
		return result == 1;
	}

	@Transactional
	public boolean removeAssignedUser(Long ticketId) {
		int result = repo.unassignUser(ticketId);
		log.info("Result : " + result);
		return result == 1;
	}

}
