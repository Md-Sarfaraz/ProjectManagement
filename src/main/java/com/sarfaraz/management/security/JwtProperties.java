package com.sarfaraz.management.security;

import java.util.concurrent.TimeUnit;

public class JwtProperties {

	// TODO : Temporarily increase token Expiration duration
	public static final String SECRET_KEY = "project_secret";
	public static final long REFRESH_EXPIRATION_TIME = TimeUnit.DAYS.toMillis(90);
	public static final long ACCESS_EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(180);
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String REFRESH_TOKEN_URL = "/api/token/refresh";
	public static final String LOGIN_URL = "/api/login";

}
