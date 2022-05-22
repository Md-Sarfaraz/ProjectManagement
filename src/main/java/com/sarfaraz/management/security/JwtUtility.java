package com.sarfaraz.management.security;

import static java.util.Arrays.stream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.sarfaraz.management.model.User;

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
		return authorities;
	}

	public String generateRefreshToken(String username, String issuer) {
		String token = JWT.create().withSubject(username)
				.withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.REFRESH_EXPIRATION_TIME))
				.withIssuer(issuer).sign(getAlgorithm());
		log.info("Refresh Token Generated : {}", token);
		return token;
	}

	public String generateAccessToken(User user, String issuer) {
		String token = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.ACCESS_EXPIRATION_TIME))
				.withIssuer(issuer).withClaim("roles", new ArrayList<>(user.getRoles()))
				.withClaim("email", user.getEmail()).withClaim("name", user.getName()).withClaim("id", user.getId())
				.sign(getAlgorithm());
		log.info("Access Token Generated : {}", token);
		return token;
	}

}
