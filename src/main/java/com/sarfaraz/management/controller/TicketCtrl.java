package com.sarfaraz.management.controller;

import com.sarfaraz.management.exception.UserNotFoundException;
import com.sarfaraz.management.model.Project;
import com.sarfaraz.management.model.Ticket;
import com.sarfaraz.management.repository.TicketRepo;
import com.sarfaraz.management.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

public class TicketCtrl {/*

    private final TicketService service;
    private final Logger log = LoggerFactory.getLogger(Ticket.class);

    @Autowired
    public TicketCtrl(TicketService service) {
        this.service = service;
    }


    @GetMapping({"/{id}"})
    public String getSingle(@PathVariable("id") Long id) {
        return "ticket/tickets";
    }

    @GetMapping({"/all", ""})
    public String getAllProjects() {
        return "ticket/tickets";
    }

    @GetMapping({"/detail/{id}", "/view/{id}"})
    public String detail(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("ticketid", id);
        Optional<Ticket> opt = service.get(id);
        model.addAttribute("ticket", opt.orElseThrow(UserNotFoundException::new));
        return "ticket/ticket-detail";
    }

    @GetMapping({"/edit/{id}"})
    public String update(@PathVariable(value = "id", required = false) Long id, Model model) {
        model.addAttribute("tickettypes", TicketRepo.Types.values());
        Optional<Ticket> opt = service.get(id);
        model.addAttribute("ticket", opt.orElse(new Ticket()));
        return "ticket/ticket-update";
    }


    @GetMapping({"/add"})
    public String getAddPage(@RequestParam(value = "pid") Long pid, @RequestParam(value = "pname") String pname, Model model) {
        log.info(pid + "\t:\t" + pname);
        model.addAttribute("tickettypes", TicketRepo.Types.values());
        Ticket ticket = new Ticket();
        ticket.setProject(new Project(pid, pname));
        model.addAttribute("ticket", ticket);
        return "ticket/ticket-add";
    }


    @PostMapping({"/save", "/update"})
    public String save(@Valid Ticket ticket, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            log.info("returning back " + request.getRequestURI());
            Ticket tc = (Ticket) result.getTarget();
            log.info(tc.toString());
            model.addAttribute("ticket", ticket);
            return request.getRequestURI().contains("/save") ? "ticket/ticket-add" : "ticket/ticket-update";
        }
        service.save(ticket);
        log.info(request.getRequestURI());
        return request.getRequestURI().contains("/save") ? "redirect:/ticket/all" :
                "redirect:/ticket/view/" + ticket.getId();
    }


    @PostMapping({"/delete"})
    public String delete(@RequestParam("id") Long id) {
        return "project/projects";
    }*/


}
