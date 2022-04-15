package com.sarfaraz.management.security;

import org.springframework.context.annotation.Bean;

import com.auth0.jwt.algorithms.Algorithm;

import lombok.extern.slf4j.Slf4j;


public class AuthAlgorithm {

	
	public Algorithm getAlgorithm() {
		Algorithm algorithm = Algorithm.HMAC256(getAlgorithmSecret().getBytes());
		return algorithm;
	}

	/**
	 * It has to be some encypted value but for learning purpose I use plain String
	 * @return Plain(Not Encrypted) String Value
	 */
	public String getAlgorithmSecret() {
		return "project_secret";
	}

}
