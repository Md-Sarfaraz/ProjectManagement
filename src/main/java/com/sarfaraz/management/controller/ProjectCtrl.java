package com.sarfaraz.management.controller;

import com.sarfaraz.management.exception.UserNotFoundException;
import com.sarfaraz.management.model.Project;
import com.sarfaraz.management.repository.ProjectRepo;
import com.sarfaraz.management.service.ProjectService;
import com.sarfaraz.management.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping({"/project"})
public class ProjectCtrl {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ProjectService service;
    private final UserService userService;

    @Autowired
    public ProjectCtrl(ProjectService projectService, UserService userService) {
        this.service = projectService;
        this.userService = userService;
    }


    @GetMapping({"/view/{id}"})
    public String getSingle(@PathVariable Long id, Model model) {
        Optional<Project> opt = service.getOne(id);
        opt.orElseThrow(() -> new UserNotFoundException(("Can't find any Project with ID : " + id)));
        opt.ifPresent(p -> model.addAttribute("project", p));
        return "project/project-detail";
    }


    @GetMapping({"/all", "/", ""})
    public String getAllProjects(Model model) {
        return "project/projects";
    }


    @GetMapping({"/edit/{id}"})
    public String updatePage(@PathVariable Long id, Model model) {
        Optional<Project> opt = service.getOne(id);
        opt.orElseThrow(() -> new UserNotFoundException(("Can't find any Project with ID : " + id)));
        opt.ifPresent(p -> {
            model.addAttribute("project", p);
            model.addAttribute("statuslist", ProjectRepo.Status.values());
            model.addAttribute("members", userService.listAll());
        });
        return "project/edit-project";
    }

    @PostMapping(path = {"/save", "/update"})
    public String addProject(@Valid Project project, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            log.info("returning back the same form ...");
            Project p = (Project) result.getTarget();
            log.info(p.toString());
            model.addAttribute("statuslist", ProjectRepo.Status.values());
            result.getAllErrors().forEach(er -> System.out.println(er.getDefaultMessage()));
            return request.getRequestURI().contains("/save") ? "project/add-project" : "project/edit-project";

        }
        service.save(project);
        return request.getRequestURI().contains("/save") ? "redirect:/project/all" :
                "redirect:/project/view/" + project.getId();

    }

    @GetMapping({"/add"})
    public String add(Model model) {
        model.addAttribute("statuslist", ProjectRepo.Status.values());
        model.addAttribute("project", new Project());

        return "project/add-project";
    }

    @PostMapping({"/delete"})
    @ResponseBody
    public ResponseEntity<String> delete(@RequestParam Long id) {
        service.delete(id);
        log.info("Project with ID : " + id + " Delete Successful");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = {"/test"})
    public void test() {
    }

}
