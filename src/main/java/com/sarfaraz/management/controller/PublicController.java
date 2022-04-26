package com.sarfaraz.management.controller;

import static com.sarfaraz.management.security.JwtProperties.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarfaraz.management.exception.UserNotFoundException;
import com.sarfaraz.management.model.AuthUser;
import com.sarfaraz.management.model.User;
import com.sarfaraz.management.security.JwtUtility;
import com.sarfaraz.management.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = { "", "/" })
@RequiredArgsConstructor
public class PublicController {

	private final JwtUtility utility;
	private final UserService userService;
	private final AuthenticationManager authenticationManager;

	@RequestMapping(value = { "/", "", "/index", "/home" })
	private Map<String, Object> index() {
		return Map.of("status", true, "page", "Home Page");
	}

	@RequestMapping(value = { "/api/login" }, method = RequestMethod.POST, consumes = "application/json")
	private ResponseEntity<Map<String, Object>> userLogin(@RequestBody AuthUser authUser, HttpServletRequest request)
			throws UserNotFoundException {
		// TODO throws global error
		Optional<User> opt = userService.findByUsername(authUser.getUsername());
		log.warn("User : {}", opt.get());
		opt.orElseThrow(() -> new UserNotFoundException("No User found with username : " + authUser.getUsername()));
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authUser.getUsername(), authUser.getPassword()));
		String token = utility.generateToken(authUser.getUsername(), opt.get().getRoles(), request.getServletPath(),
				true);

		log.warn("token : {}", token);
		Map<String, Object> res = new HashMap<>();

		return ResponseEntity.ok(res);

	}

	@GetMapping("/api/token/refresh")
	private void refreshToke(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String authHeader = request.getHeader(AUTHORIZATION);
		if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
			try {
				String refresh_token = authHeader.substring("Bearer ".length());
				String username = utility.getSubject(refresh_token);
				Optional<User> opt = userService.findByUsername(username);
				User user = opt.orElseThrow(() -> new RuntimeException("User Not Found with username : " + username));
				String access_token = utility.generateToken(user.getUsername(), user.getRoles(),
						request.getRequestURL().toString(), true);

				response.setHeader("access_tokec", access_token);
				response.setHeader("refresh_token", refresh_token);
				Map<String, String> tokens = new HashMap<>();
				tokens.put("access_token", access_token);
				tokens.put("refresh_token", refresh_token);
				response.setContentType(APPLICATION_JSON_VALUE);
				log.info("Return New Access Token with Same Refresh Token : {}", tokens.toString());
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);

			} catch (Exception e) {
				log.error("Error logging in: {}", e.getMessage());
				response.setHeader("error", e.getMessage());
				response.setStatus(FORBIDDEN.value());
				Map<String, String> error = new HashMap<>();
				error.put("error_message", e.getMessage());
				response.setContentType(APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}

		} else {

			throw new RuntimeException("Refresh Token is missing");
		}

		// boolean res = userService.addRole(1l, Roles.ROLE_MANAGER.name());
		// log.warn("Update User Role : {}", res);

	}

}
