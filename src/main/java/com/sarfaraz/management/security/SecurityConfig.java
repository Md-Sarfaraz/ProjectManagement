package com.sarfaraz.management.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;
	private final PasswordConfig passwordConfig;
	private final InvalidUserAuthEntryPoint authEntryPoint;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordConfig.encoder());
		// auth.userDetailsService(userDetailsService);

	}

	/**
	 * this part is more tricky than I thought, BE CAREFUL...
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// JwtAuthentictionFilter authentictionFilter = new
		// JwtAuthentictionFilter(authenticationManagerBean());
		// authentictionFilter.setFilterProcessesUrl("/api/login");
		http.cors().configurationSource(request -> {
			var cors = new CorsConfiguration();
			cors.setAllowedOrigins(List.of("*"));
			cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
			cors.setAllowedHeaders(List.of("*"));
			return cors;
		});

		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers(HttpMethod.POST, JwtProperties.LOGIN_URL).permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, JwtProperties.REFRESH_TOKEN_URL).permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/index", "/home", "/api/info", "/api/select-properties")
				.permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/user/roles/**").hasAnyAuthority("ROLE_ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/ticket/**").hasAnyAuthority("ROLE_ADMIN",
				"ROLE_MANAGER");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/user/delete").hasAnyAuthority("ROLE_USER",
				"ROLE_ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/project/**").hasAnyAuthority("ROLE_ADMIN");
		http.authorizeRequests().anyRequest().authenticated();
		http.exceptionHandling().authenticationEntryPoint(authEntryPoint);

		// http.addFilter(authentictionFilter);
		http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
