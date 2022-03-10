package com.sarfaraz.management.service;

import com.sarfaraz.management.model.Role;
import com.sarfaraz.management.model.Ticket;
import com.sarfaraz.management.model.TicketType;
import com.sarfaraz.management.model.dto.TicketListDTO;
import com.sarfaraz.management.model.dto.TicketListProjectDTO;
import com.sarfaraz.management.repository.TicketRepo;
import com.sarfaraz.management.repository.TicketTypeRepo;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public class TicketService {

    private final TicketRepo repo;
    private final TicketTypeRepo typeRepo;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public TicketService(TicketRepo repo, TicketTypeRepo typeRepo) {
        this.repo = repo;
        this.typeRepo = typeRepo;
    }


    @Transactional
    public List<TicketListDTO> listAll() {
        return repo.getAllDTO();
    }
/*
    @Transactional
    public void save(Ticket t) {
        if (t.getCreated() == null) t.setCreated(LocalDate.now());
        if (t.getCreated() != null) t.setUpdated(LocalDate.now());
        typeRepo.save(t.getType());
        repo.save(t);
    }

    public Optional<Ticket> get(Long id) {
        return repo.findById(id);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
*/
    public List<TicketListProjectDTO> getAllTicketsRelatedToProject(Long id) {
        return repo.getRelatedTickets(id);
    }

    public void test() {
        List<TicketListDTO> dtos = repo.getAllDTO();
        log.info(String.valueOf(dtos.size()));
    }
}
