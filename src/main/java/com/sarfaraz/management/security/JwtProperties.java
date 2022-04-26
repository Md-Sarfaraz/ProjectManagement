package com.sarfaraz.management.security;

import java.util.concurrent.TimeUnit;

public class JwtProperties {

	public static final String SECRET_KEY = "project_secret";
	public static final long EXPIRATION_TIME = TimeUnit.HOURS.toMillis(48);
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String REFRESH_TOKEN_URL = "/api/token/refresh";
	public static final String LOGIN_URL = "/api/login";

}
