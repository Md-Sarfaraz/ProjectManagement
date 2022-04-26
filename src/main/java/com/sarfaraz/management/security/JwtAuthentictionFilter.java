package com.sarfaraz.management.security;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarfaraz.management.controller.advice.ErrorDetail;
import com.sarfaraz.management.model.User;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthentictionFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;

	private JwtUtility utility;

	public JwtAuthentictionFilter() {
	}

	public JwtAuthentictionFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		this.utility = new JwtUtility();
	}

	public void setAuthenticationManager(AuthenticationManager authM) {
		this.authenticationManager = authM;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String username;
		String password;
		if (request.getContentType().equalsIgnoreCase(APPLICATION_JSON_VALUE.toString())) {
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				// objectMapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
				Map<String, String> requestMap = objectMapper.readValue(request.getInputStream(), Map.class);
				username = requestMap.get("username");
				password = requestMap.get("password");
			} catch (IOException e) {
				throw new AuthenticationServiceException(e.getMessage(), e);
			}
		} else {
			username = request.getParameter("username");
			password = request.getParameter(getPasswordParameter());
		}

		log.info("Username : {} , Password : {}", username, password);
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		return authenticationManager.authenticate(authenticationToken);
	}

	// TODO : Change UserDetails to model.User
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
//		User user = (User) authResult.getPrincipal();

		// String access_token = utility.generateToken(user,
		// request.getRequestURL().toString(), true);
		// String refresh_token = utility.generateToken(user,
		// request.getRequestURL().toString(), false);

		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult
				.getPrincipal();

		log.warn("Model.User : {} : {}", authResult.getPrincipal(), authResult.getName());
		String access_token = "";
		String refresh_token = "";

		response.setContentType(APPLICATION_JSON_VALUE);
		response.setHeader("access-token", access_token);
		response.setHeader("refresh-token", refresh_token);
		Map<String, Object> returnValue = new HashMap<>();
		returnValue.put("accessToken", access_token);
		returnValue.put("refreshToken", refresh_token);
		// returnValue.put("user", user);
		log.info("Return Token Map keys : {}", returnValue.keySet().toString());
		new ObjectMapper().writeValue(response.getOutputStream(), returnValue);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		response.setContentType(APPLICATION_JSON_VALUE);
		log.error(failed.getMessage());
		ErrorDetail errorDetail = new ErrorDetail(failed.getMessage(), HttpStatus.UNAUTHORIZED.name(),
				HttpStatus.UNAUTHORIZED.value(), request.getServletPath());
		new ObjectMapper().writeValue(response.getOutputStream(), errorDetail);

	}

}
