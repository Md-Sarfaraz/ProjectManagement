package com.sarfaraz.management.config;
/*
import com.sarfaraz.management.service.AppUserDetailsService;
import com.sarfaraz.management.util.MySuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import java.util.concurrent.TimeUnit;
*/
//@Configuration
//@EnableWebSecurity
public class WebSecurity {
	/*
	 *  ###extends WebSecurityConfigurerAdapter
	 *
	 * private final AppUserDetailsService userDetailsService; private final
	 * PasswordEncoder encoder; private final MySuccessHandler successHandler;
	 * 
	 * @Autowired public WebSecurity(AppUserDetailsService userDetailsService,
	 * PasswordEncoder encoder, MySuccessHandler successHandler) {
	 * this.userDetailsService = userDetailsService; this.encoder = encoder;
	 * this.successHandler = successHandler; }
	 * 
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { // auth.userDetailsService(userDetailsService);
	 * auth.authenticationProvider(authenticationProvider()); }
	 * 
	 * @Bean public DaoAuthenticationProvider authenticationProvider() {
	 * DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	 * provider.setUserDetailsService(userDetailsService);
	 * provider.setPasswordEncoder(encoder); return provider; }
	 * 
	 * @Override protected void configure(HttpSecurity http) throws Exception {
	 * http.authorizeRequests().antMatchers("/dist/**", "/plugins/**",
	 * "/files/**").permitAll() .antMatchers(HttpMethod.GET, "", "/", "/index",
	 * "/login*", "/home").permitAll() .antMatchers(HttpMethod.POST, "/user/**",
	 * "/project/**", "/bug/**").permitAll()
	 * .antMatchers("/user/update/roles").permitAll().anyRequest().authenticated().
	 * and()
	 * 
	 * .csrf().disable().cors().and()
	 * 
	 * .formLogin().loginPage("/login").permitAll().successHandler(successHandler)
	 * 
	 * .failureUrl("/login?error=true").permitAll().and().rememberMe()
	 * .tokenValiditySeconds((int)
	 * TimeUnit.DAYS.toSeconds(7)).key("myRandomKeY").and().logout()
	 * .logoutUrl("/logout").logoutSuccessUrl("/login?logout=true"); }
	 */
}
