package com.sarfaraz.management.controller;

import com.sarfaraz.management.exception.UserNotFoundException;
import com.sarfaraz.management.model.Role;
import com.sarfaraz.management.model.User;
import com.sarfaraz.management.model.dto.NameAndRole;
import com.sarfaraz.management.repository.RoleRepo;
import com.sarfaraz.management.service.UserService;
import com.sarfaraz.management.util.Helper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = { "/user" })
public class UserController {
	private final UserService userService;
	private final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/all/with/roles")
	public Set<NameAndRole> allUserWithRoles() throws JSONException {
		return userService.listAllWithRoles();
	}

	@GetMapping(value = { "/all", "/" })
	public List<User> test() throws JSONException {
		List<User> users = userService.listAll();
		return users;
	}

	@GetMapping(value = { "/list"})
	public List<User> getSortedPageable(final @RequestParam("page") int page, final @RequestParam("size") int size)
			throws JSONException {
		Page<User> users = userService.sortedByName(page, size);
		return users.toList();
	}
	@GetMapping(value = { "/list"})
	public List<User> getSortedPageable(final @RequestParam("page") int page, final @RequestParam("size") int size,
			final @RequestParam("size") String sort	)
			throws JSONException {
		Page<User> users = userService.sortedByName(page, size);
		return users.toList();
	}

	@GetMapping(path = { "/view/{id}" })
	public User getSingleUser(final @PathVariable("id") long id) {
		Optional<User> opt = userService.getOne(id);
		opt.orElseThrow(() -> new UserNotFoundException(("Can't find any Member with ID : " + id)));
		return opt.get();
	}

	@GetMapping(value = { "/all/users/roles" })
	public List<User> getAllWithroles() {
		return userService.listAll();
	}

	@RequestMapping(value = "/roles", method = RequestMethod.POST)
	public ResponseEntity<String> updateUserRoles(final @RequestParam("name") String name,
			final @RequestParam("email") String email, final @RequestParam("roles[]") String[] roleArr) {
		// %5B%5D = [ ] (in request parameter)
		Set<Role> roles = new HashSet<>();
		for (String r : roleArr) {
			roles.add(new Role(RoleRepo.Roles.valueOf(r).name(), RoleRepo.Roles.valueOf(r).getDetails()));
		}
		userService.updateRole(name, email, roles);
		return new ResponseEntity<>("Done", HttpStatus.OK);
	}
}
