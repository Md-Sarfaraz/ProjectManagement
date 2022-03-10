package com.sarfaraz.management.controller;

import com.sarfaraz.management.model.Project;
import com.sarfaraz.management.model.Ticket;
import com.sarfaraz.management.model.User;
import com.sarfaraz.management.model.dto.NameAndRole;
import com.sarfaraz.management.model.dto.TicketListProjectDTO;
import com.sarfaraz.management.service.ProjectService;
import com.sarfaraz.management.service.TicketService;
import com.sarfaraz.management.service.UserService;
import com.sarfaraz.management.util.Helper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping({"/project/rest"})
public class ProjectController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ProjectService service;
    private final TicketService ticketService;

    @Autowired
    public ProjectController(ProjectService service, TicketService ticketService) {
        this.service = service;
        this.ticketService = ticketService;
    }

    @GetMapping(value = {"/all"})
    public String getAllProject() {
        JSONArray array = new JSONArray();
        List<Project> list = service.listAll();
        list.forEach(p -> {
            JSONObject object = new JSONObject();
            try {
                object.put("id", p.getId());
                object.put("name", p.getName());
                object.put("detail", p.getDetail());
                object.put("lastDate", Helper.getArrayofDate(p.getLastDate()));
                object.put("status", p.getStatus());
                object.put("created", Helper.getArrayofDate(p.getCreated()));
                object.put("updated", Helper.getArrayofDate(p.getUpdated()));
                array.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        return array.toString();

    }


    @GetMapping({"/detail/users"})
    public Set<NameAndRole> getMembers(@RequestParam("id") Long id) {
        return service.getAllRelatedUsers(id);
    }


    @GetMapping({"/detail/tickets"})
    public List<TicketListProjectDTO> getTickets(@RequestParam("id") Long id) {
        log.info("return from detail/tickets");
        return ticketService.getAllTicketsRelatedToProject(id);
    }

    @PostMapping({"/user/add"})
    public ResponseEntity<String> addUsertoProject(@RequestParam("pid") Long pid, @RequestParam("uid") Long uid) {
        service.addUserToProject(pid, uid);
        log.info(pid + " : " + uid);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping({"/user/delete"})
    public ResponseEntity<String> deleteUserFromProject(@RequestParam("pid") Long pid, @RequestParam("uid") Long uid) {
        service.removeUserFromProject(pid, uid);
        log.info("In Delete :: pid : " + pid + ", Uid : " + uid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
