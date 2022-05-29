package com.sarfaraz.management.service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sarfaraz.management.model.Ticket;
import com.sarfaraz.management.model.dto.TicketListDTO;
import com.sarfaraz.management.model.selects.TicketPriority;
import com.sarfaraz.management.model.selects.TicketStatus;
import com.sarfaraz.management.model.selects.TicketType;
import com.sarfaraz.management.repository.TicketRepo;

@Service
public class TicketService {

	private final TicketRepo repo;
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public TicketService(TicketRepo repo) {
		this.repo = repo;

	}

	@Transactional
	public boolean save(Ticket ticket) {
		if (ticket.getProject().getId() == null)
			return false;
		if (ticket.getSubmitter() == null)
			return false;
		ticket.setUpdated(LocalDate.now());
		if (ticket.getId() == null) {
			ticket.setCreated(LocalDate.now());
		}
		if (ticket.getType() == null) {
			ticket.setType(TicketType.ISSUE);
		}

		if (ticket.getStatus() == null) {
			ticket.setStatus(TicketStatus.HOLD);
		}

		if (ticket.getPriority() == null) {
			ticket.setPriority(TicketPriority.MEDIUM);
		}

		log.warn(ticket.toString());
		Ticket t = repo.save(ticket);

		return t.getId() > 0;
	}

	@Transactional
	public Page<TicketListDTO> listAll(int page, int size) {
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by("name"));
		return repo.getAllTickets(pageable);
	}

	public Page<TicketListDTO> getAllTicketsByProjectId(Long id, int page, int size) {
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by("name"));
		Page<TicketListDTO> tickets = repo.getAllByProjectId(id, pageable);
		log.info(tickets.toString());
		return tickets;
	}

	public Set<TicketListDTO> findAllByUserId(Long id) {
		Set<TicketListDTO> tickets = repo.findAllByUserId(id);
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
