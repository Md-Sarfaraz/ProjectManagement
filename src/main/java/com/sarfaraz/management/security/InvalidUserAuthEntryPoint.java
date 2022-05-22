package com.sarfaraz.management.security;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class InvalidUserAuthEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		if (!response.isCommitted()) {
			Map<String, Object> value = Map.of("error", "Valid Access Token Required");
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			new ObjectMapper().writeValue(response.getOutputStream(), value);
			//response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Login Failed");
		} else {

			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

}
