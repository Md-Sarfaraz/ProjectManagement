package com.sarfaraz.management.controller;

import com.sarfaraz.management.model.Ticket;
import com.sarfaraz.management.model.dto.TicketListDTO;
import com.sarfaraz.management.model.dto.TicketListProjectDTO;
import com.sarfaraz.management.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ticket/rest/")
public class TicketController {

    private final Logger log = LoggerFactory.getLogger(Ticket.class);
    private final TicketService service;

    @Autowired
    public TicketController(TicketService service) {
        this.service = service;
    }

    @GetMapping(path = {"/all"})
    public List<TicketListDTO> getAllTicketsJson() {
        return service.listAll();
    }


    @GetMapping(path = {"/related"})
    public List<TicketListProjectDTO> getAllTicketsRelatedToProject(@RequestParam("id") Long id) {
        return service.getAllTicketsRelatedToProject(id);
    }

}
