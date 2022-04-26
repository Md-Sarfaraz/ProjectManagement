package com.sarfaraz.management.security;

import static com.sarfaraz.management.security.JwtProperties.SECRET_KEY;
import static java.util.Arrays.stream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtility {

	public Algorithm getAlgorithm() {
		return Algorithm.HMAC256(JwtProperties.SECRET_KEY.getBytes());
	}

	public boolean validateToken(String token, String username) {
		String tokenUsername = getSubject(token);
		return (username.equals(tokenUsername) && !isTokenExpired(token));
	}

	public boolean isTokenExpired(String token) {
		Date expDate = getExpirationDate(token);
		return expDate.before(new Date(System.currentTimeMillis()));
	}

	public Date getExpirationDate(String token) {
		return decodeToken(token).getExpiresAt();
	}

	public String getSubject(String token) {
		return decodeToken(token).getSubject();
	}

	public DecodedJWT decodeToken(String token) {
		Algorithm algorithm = getAlgorithm();
		JWTVerifier verifier = JWT.require(algorithm).build();
		return verifier.verify(token);
	}

	public Claim readClaims(String token) {
		return decodeToken(token).getClaim("roles");
	}

	public Collection<SimpleGrantedAuthority> getAuthorities(String token) {

		String[] roles = readClaims(token).asArray(String.class);
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		stream(roles).forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role));
		});
		log.info("return Authorities : {}", authorities.toString());
		return authorities;
	}

	public String generateToken(User user, String issuer, boolean withClaims) {
		String token;
		if (withClaims) {
			token = JWT.create().withSubject(user.getUsername())
					.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)).withIssuer(issuer)
					.withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
							.collect(Collectors.toList()))
					.sign(getAlgorithm());
		} else {
			token = JWT.create().withSubject(user.getUsername())
					.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)).withIssuer(issuer)
					.sign(getAlgorithm());
		}

		log.info("New Token Generated : {}", token);
		return token;
	}

	public <T> String generateToken(String username, Set<String> roles, String issuer, boolean withClaims) {
		String token;
		if (withClaims) {
			token = JWT.create().withSubject(username).withExpiresAt(getForwardTime(15)).withIssuer(issuer)
					.withClaim("roles", new ArrayList<>().addAll(roles)).sign(getAlgorithm());
		} else {
			token = JWT.create().withSubject(username).withExpiresAt(getForwardDays(90)).withIssuer(issuer)
					.sign(getAlgorithm());
		}

		log.info("New Token Generated : {}", token);
		log.info("secret : {}", SECRET_KEY);
		return token;
	}

	public Date getForwardTime(int minute) {
		Date date = new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(minute));
		return date;
	}

	public Date getForwardDays(int days) {
		Date date = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMicros(days));
		return date;
	}

}
