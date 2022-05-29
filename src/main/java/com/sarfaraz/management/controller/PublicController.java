package com.sarfaraz.management.controller;

import static com.sarfaraz.management.security.JwtProperties.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarfaraz.management.exception.CredentialsException;
import com.sarfaraz.management.exception.UserNotFoundException;
import com.sarfaraz.management.model.AuthUser;
import com.sarfaraz.management.model.User;
import com.sarfaraz.management.model.dto.ProjectOnlyDTO;
import com.sarfaraz.management.model.dto.TicketListDTO;
import com.sarfaraz.management.model.dto.TotalCounts;
import com.sarfaraz.management.security.JwtProperties;
import com.sarfaraz.management.security.JwtUtility;
import com.sarfaraz.management.service.DataInfoService;
import com.sarfaraz.management.service.ProjectService;
import com.sarfaraz.management.service.TicketService;
import com.sarfaraz.management.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = { "/" })
@RequiredArgsConstructor
public class PublicController {

	private final JwtUtility utility;
	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	private final ProjectService projectService;
	private final TicketService ticketService;

	@RequestMapping(value = { "/index", "/home" })
	private Map<String, Object> index(@RequestParam(required = false) Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {

		} catch (IllegalStateException e) {
			e.printStackTrace();
		}

		return map;
	}

	@RequestMapping(value = { "/api/info" }, method = RequestMethod.GET)
	private ResponseEntity<Map<String, Object>> getRelatedInfo(
			@RequestParam(value = "uid", required = false) final Long uid) {
		Map<String, Object> map = new HashMap<>();
		if (uid != null) {
			User user = userService.getOne(uid)
					.orElseThrow(() -> new UserNotFoundException("No User Found with ID : " + uid));
			Set<ProjectOnlyDTO> projects = projectService.getAllProjectsByUserId(user.getId());
			Set<TicketListDTO> tickets = ticketService.findAllByUserId(user.getId());
			map.put("relatedProjects", projects);
			map.put("relatedTicket", tickets);
			map.put("TotalRelatedProject", projects == null ? 0 : projects.size());
			map.put("totalRelatedTicket", tickets == null ? 0 : tickets.size());
		}

		TotalCounts counts = projectService.totalCounts();
		map.put("totalProject", counts.getProjectsCount());
		map.put("totalTicket", counts.getTicketsCount());
		map.put("totalUsers", counts.getUsersCount());

		return ResponseEntity.ok(map);
	}

	@RequestMapping(value = { "/api/select-properties" }, method = RequestMethod.GET)
	private ResponseEntity<Map<String, Object>> getselectOptions() {
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> tickets = new HashMap<>();
		tickets.put("status", DataInfoService.getTicketStatus());
		tickets.put("type", DataInfoService.getTicketType());
		tickets.put("priority", DataInfoService.getTicketPriority());

		map.put("users", Map.of("roles", DataInfoService.getUserRoles()));
		map.put("tickets", tickets);
		map.put("projects", Map.of("status", DataInfoService.getProjectStatus()));
		return ResponseEntity.ok(map);
	}

	@RequestMapping(value = JwtProperties.LOGIN_URL, method = RequestMethod.POST, consumes = "application/json")
	private ResponseEntity<Map<String, Object>> userLogin(@RequestBody AuthUser authUser, HttpServletRequest request)
			throws CredentialsException {

		Map<String, Object> res = new HashMap<>();

		Optional<User> opt = userService.findByUsername(authUser.getUsername());
		opt.orElseThrow(() -> new CredentialsException("Wrong Username : " + authUser.getUsername()));

		opt.ifPresentOrElse(user -> {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authUser.getUsername(), authUser.getPassword()));
			String accessToken = utility.generateAccessToken(opt.get(), request.getServletPath());
			String refreshToken = utility.generateRefreshToken(authUser.getUsername(), request.getServletPath());
			res.put("token", Map.of("accessToken", accessToken, "refreshToken", refreshToken));
			log.warn("UserLog : {}", user);
			res.put("user", user);
		}, () -> {
			log.warn("Username Not Found log: {}", authUser.getUsername());
		});

		Set<ProjectOnlyDTO> projects = projectService.getAllProjectsByUserId(opt.get().getId());
		Set<TicketListDTO> tickets = ticketService.findAllByUserId(opt.get().getId());
		TotalCounts counts = projectService.totalCounts();

		res.put("relatedProjects", projects);
		res.put("relatedTicket", tickets);

		if (projects != null)
			res.put("TotalRelatedProject", projects.size());
		if (tickets != null)
			res.put("totalRelatedTicket", tickets.size());
		res.put("totalProject", counts.getProjectsCount());
		res.put("totalTicket", counts.getTicketsCount());
		res.put("totalUsers", counts.getUsersCount());

		return ResponseEntity.ok(res);
	}

	@GetMapping(value = JwtProperties.REFRESH_TOKEN_URL)
	private void refreshToke(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String authHeader = request.getHeader(AUTHORIZATION);
		if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
			response.setContentType(APPLICATION_JSON_VALUE);
			try {

				String refresh_token = authHeader.substring("Bearer ".length());
				String username = utility.getSubject(refresh_token);

				Optional<User> opt = userService.findByUsername(username);
				User user = opt.orElseThrow(() -> new RuntimeException("User Not Found !"));
				String access_token = utility.generateAccessToken(user, request.getRequestURL().toString());
				response.setHeader("access_token", access_token);
				response.setHeader("refresh_token", refresh_token);
				Map<String, String> tokens = new HashMap<>();
				tokens.put("accessToken", access_token);
				tokens.put("refreshToken", refresh_token);
				log.info("Return New Access Token with Same Refresh Token : {}", tokens.toString());
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);
			} catch (Exception e) {
				log.error("Error logging in Refresh Token : {}", e.getMessage());
				response.setHeader("error", e.getMessage());
				response.setStatus(FORBIDDEN.value());
				Map<String, String> error = new HashMap<>();
				error.put("error_message", e.getMessage());

				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}

		} else {

			throw new RuntimeException("Refresh Token is missing");
		}

		// boolean res = userService.addRole(1l, Roles.ROLE_MANAGER.name());
		// log.warn("Update User Role : {}", res);

	}

}
