package com.sarfaraz.management.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sarfaraz.management.exception.UserNotFoundException;
import com.sarfaraz.management.model.ResponseData;
import com.sarfaraz.management.model.Role;
import com.sarfaraz.management.model.User;
import com.sarfaraz.management.model.dto.NameAndRole;
import com.sarfaraz.management.model.dto.UserAllInfo;
import com.sarfaraz.management.model.dto.UserOnlyDTO;
import com.sarfaraz.management.repository.RoleRepo;
import com.sarfaraz.management.service.UserService;

@RestController
@RequestMapping(value = { "/api/user" })
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

	@GetMapping(value = { "/list" })
	public ResponseEntity<ResponseData> getSortedPageable(
			final @RequestParam(value = "page", required = false, defaultValue = "1") int page,
			final @RequestParam(value = "size", required = false, defaultValue = "20") int size,
			final @RequestParam(value = "sort", required = false, defaultValue = "name") String sort)
			throws JSONException {
		Page<UserOnlyDTO> users = userService.sortedByfield(page, size, sort);

		ResponseData response = new ResponseData(users.getTotalPages(), users.getTotalElements(), users.getSize(),
				users.getNumber() + 1, users.toList());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(path = { "/view/{id}" })
	public User getSingleUser(final @PathVariable("id") long id) {
		Optional<User> opt = userService.getOne(id);
		opt.orElseThrow(() -> new UserNotFoundException(("Can't find any Member with ID : " + id)));
		return opt.get();
	}

	@GetMapping(path = { "/view/info/{id}" })
	public UserAllInfo getSingleUserInfo(final @PathVariable("id") long uid) {
		UserAllInfo user = userService.getAllwithprojectandroles(uid);
		return user;
	}

	@GetMapping(path = { "/search" })
	public ResponseEntity<ResponseData> searchUserByField(final @RequestParam("name") Optional<String> name,
			
			final @RequestParam(value = "page", required = false, defaultValue = "1") int page,
			final @RequestParam(value = "size", required = false, defaultValue = "10") int size,
			final @RequestParam(value = "sort", required = false, defaultValue = "name") String sort) {

		Page<User> users = userService.findByName(name.get(), page, size, sort);

		log.info(users.toList().toString());

		ResponseData response = new ResponseData(users.getTotalPages(), users.getTotalElements(), users.getSize(),
				users.getNumber() + 1, users.toList());
		return new ResponseEntity<>(response, HttpStatus.OK);
//		Map<String, String> user = Map.of("name", name.orElse("Null"), "email", email.orElse("Null"));
		// return users;
	}

	@RequestMapping(value = {"/save"}, method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Map<String, Long>> save(@RequestBody User user) {
		// log.error(user.toString());
		long id = userService.save(user);
		Map<String, Long> res = Map.of("id", id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Map<String, Long>> updateUser(@RequestBody User user) {
		// log.error(user.toString());
		long id = userService.updateUser(user);
		Map<String, Long> res = Map.of("id", id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/password", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Map<String, Boolean>> updateUserPassword(@RequestBody Map<String, Object> obj) {
		boolean status = false;
		Integer id = (Integer) obj.get("id");
		String oldpass = (String) obj.get("oldpassword");
		String newpass = (String) obj.get("newpassword");
		log.error(id.toString() + " " + oldpass + " " + newpass);
		if (oldpass.isBlank() || newpass.length() < 3) {
			throw new UserNotFoundException("Can't Update Your Password, Please try again.");
		} else {
			status = userService.updatePassword(id.longValue(), oldpass, newpass);
		}
		Map<String, Boolean> res = Map.of("status", status);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseEntity<Map<String, Boolean>> deleteUser(@RequestParam("uid") Long uid) {
		try {
			userService.delete(uid);
		} catch (Exception e) {
			throw new UserNotFoundException("Can't find any Member with ID : " + uid, e.getCause());
		}
		Map<String, Boolean> res = Map.of("status", true);
		return new ResponseEntity<>(res, HttpStatus.OK);
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
