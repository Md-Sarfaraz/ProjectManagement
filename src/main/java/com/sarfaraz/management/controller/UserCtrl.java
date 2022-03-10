package com.sarfaraz.management.controller;

import com.sarfaraz.management.exception.UserNotFoundException;
import com.sarfaraz.management.model.FileInfo;
import com.sarfaraz.management.model.Role;
import com.sarfaraz.management.model.User;
import com.sarfaraz.management.model.dto.OnlyNameAndEmail;
import com.sarfaraz.management.repository.RoleRepo.Roles;
import com.sarfaraz.management.service.FileStorageService;
import com.sarfaraz.management.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;/*
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;*/
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;


public class UserCtrl {

    private final Logger log = LoggerFactory.getLogger(UserCtrl.class);
    private final FileStorageService storageService;
    private final UserService userService;

    @Autowired
    public UserCtrl(UserService userService, FileStorageService storageService) {
        this.userService = userService;
        this.storageService = storageService;
    }

/*
    @GetMapping(path = {"/profile"})
    public String profile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> opt = userService.findByEmail(auth.getName());
        opt.ifPresent(user -> {
            model.addAttribute("user", user);
        });
        return "user/profile";
    }
*/
    @GetMapping(path = {"/all", "list", "/", ""})
    public String getAll() {
        return "user/users";
    }

    @GetMapping(path = {"/manage-role"})
    public String roleManage(Model model) {
        model.addAttribute("roles", Roles.values());
        return "user/user-roles";
    }

    @GetMapping(path = {"/register", "/add"})
    public String register(User user) {
        user.setAddress("Asansol Railpar");
        user.setName("Sarfaraz from This");
        user.setEmail("sarfaraz@gmail.com");
        user.setMobile("073085720");
        user.setPassword("pass");
        user.setDob(LocalDate.now());
        return "user/register";
    }


    @PostMapping(path = {"/save"})
    public String saveUser(final @RequestParam(value = "image", required = false) MultipartFile file,
                           @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "user/register";
        }
        FileInfo fileInfo = new FileInfo();
        if (!file.isEmpty()) {
            fileInfo = storageService.storeFile(file);
        }
//        user.setImagePath(fileInfo.getFileDownloadUri());
//        userService.save(user);

        return "redirect:/user/all";
    }






    @GetMapping(path = {"/view/{id}"})
    public ModelAndView getSingleUser(final @PathVariable("id") long id) {
        ModelAndView view = new ModelAndView();
        Optional<User> opt = userService.getOne(id);
        opt.orElseThrow(() -> new UserNotFoundException(("Can't find any Member with ID : " + id)));
        opt.ifPresent(user -> {
            view.addObject("user", user);
            view.setViewName("user/profile");
        });
        return view;
    }

    @PostMapping(path = {"/save/password"})
    public String updatePassword(User user, final @RequestParam("newpassword") String pass) {
        if (user.getId() > 0 && !user.getPassword().isBlank() && !pass.isBlank()) {
            boolean res = userService.updatePassword(user.getId(), user.getPassword(), pass);
            log.warn(String.format("User with ID:%s changed thier password", user.getId()));
        }

        return "redirect:/user/profile";
    }

    @PostMapping(path = {"/update"})
    public String updateUser(final @RequestParam("image") MultipartFile file, User user, BindingResult result) {
        if (result.hasErrors()) {
            return "user/register";
        }
        FileInfo fileInfo = new FileInfo();
        if (!file.isEmpty()) {
            fileInfo = storageService.storeFile(file);
        }
//        user.setImagePath(fileInfo.getFileDownloadUri());
//        userService.updateUser(user);

        return "redirect:/user/all";
    }

    @PostMapping(path = {"/delete"})
    public ResponseEntity<String> deleteUser(@RequestParam("id") Long uid) {
        //userService.delete(uid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping(path = "/search")
    @ResponseBody
    public ResponseEntity<String> getSearchedUsers(final @RequestParam("name") Optional<String> name) {
        if (name.isEmpty())
            return new ResponseEntity<>(HttpStatus.OK);
        else if (name.get().length() < 3)
            return new ResponseEntity<>("[{\"text\": \"At Least 3 Words\"}]", HttpStatus.OK);
        List<OnlyNameAndEmail> list = userService.searchUser(name.get());
        JSONArray array = new JSONArray();
        list.forEach(u -> {
            try {
                JSONObject jo = new JSONObject();
                jo.put("id", u.getId());
                jo.put("name", u.getName());
                jo.put("email", u.getEmail());
                array.put(jo);
            } catch (JSONException e) {
                log.error("Error in Search JSON");
                throw new RuntimeException(e);
            }
        });
        return new ResponseEntity<>(array.toString(), HttpStatus.OK);
    }
}
