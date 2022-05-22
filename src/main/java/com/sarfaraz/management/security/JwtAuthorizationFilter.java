package com.sarfaraz.management.security;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final JwtUtility utility;

	@Autowired
	public JwtAuthorizationFilter(JwtUtility utility) {
		this.utility = utility;
	}

	public JwtAuthorizationFilter() {
		this.utility = new JwtUtility();

	}
	
	private List<String> permitUrls() {
		List<String> urls = new ArrayList<>();
		urls.add(JwtProperties.LOGIN_URL);
		urls.add(JwtProperties.REFRESH_TOKEN_URL);
		urls.add("/select-properties");
		return urls;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if (request.getServletPath().equals(JwtProperties.LOGIN_URL)
				|| request.getServletPath().equals(JwtProperties.REFRESH_TOKEN_URL)) {
			filterChain.doFilter(request, response);

		} else {
			String authHeader = request.getHeader(AUTHORIZATION);
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				try {
					String token = authHeader.substring("Bearer ".length());

					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							utility.getSubject(token), null, utility.getAuthorities(token));
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					filterChain.doFilter(request, response);
				} catch (NullPointerException e) {
					log.error("Error logging in: {}", e.getMessage());
					response.setHeader("error",
							"Claims(roles) is empty! make sure to send access token not the refresh token");
					response.setStatus(FORBIDDEN.value());
					Map<String, String> error = new HashMap<>();
					error.put("error_message",
							"Claims(roles) is empty! make sure to send access token not the refresh token");
					response.setContentType(APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(response.getOutputStream(), error);
				} catch (TokenExpiredException e) {
					log.error("Error logging in:{}, {}", e.getMessage());
					response.setHeader("error", e.getMessage());
					response.setStatus(UNAUTHORIZED.value());
					Map<String, String> error = new HashMap<>();
					error.put("message", e.getMessage());
					error.put("error", UNAUTHORIZED.name());
					response.setContentType(APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(response.getOutputStream(), error);
				} catch (Exception e) {
					log.error("Error logging in: {}", e.getMessage());
					response.setHeader("error", e.getMessage());
					response.setStatus(FORBIDDEN.value());
					Map<String, String> error = new HashMap<>();
					error.put("message", e.getMessage());
					response.setContentType(APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(response.getOutputStream(), error);
				}

			
//			else if (authHeader == null) {
//				log.error("No Access Token Provided");
//				response.setHeader("error", "Authorization Token Missing");
//				response.setStatus(FORBIDDEN.value());
//				Map<String, String> error = new HashMap<>();
//				error.put("error_message", "Send JWT Token in the Request Header");
//				response.setContentType(APPLICATION_JSON_VALUE);
//				new ObjectMapper().writeValue(response.getOutputStream(), error);

			} else {
				filterChain.doFilter(request, response);

			}
		}

	}

}
