/**
 * 
 */
package com.java.blog.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.java.blog.web.controllers.AdminFilter;

/**
 * @author Semir
 *
 */
@Configuration
@EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true)
// @ImportResource("classpath:applicationContext-security.xml")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder registry)
			throws Exception {
		/*
		 * registry .inMemoryAuthentication() .withUser("siva") // #1
		 * .password("siva") .roles("USER") .and() .withUser("admin") // #2
		 * .password("admin") .roles("ADMIN","USER");
		 */

		// registry.jdbcAuthentication().dataSource(dataSource);
		registry.userDetailsService(customUserDetailsService);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**"); // #3
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
			.authorizeRequests()
				.antMatchers("/login", 
						"/login/form**", 
						"/register", 
						"/index",
						"/error",
						"/reset",
						"/user-account",
						"/user-detail",
						"/user-register",
						"/users",
						"/verification",
//						"/logout", 
						"/j_spring_security_check", 
						"/springmvc-datajpa-security-demo/TwoFactorAuthController",
						"/springmvc-datajpa-security-demo/ResetController"
						)
				.permitAll()
				// #4
				.antMatchers("/admin", "/admin/**").hasRole("ADMIN")
				// #6
				.anyRequest().authenticated()
				// 7
				.and()
			.formLogin()
				// #8
				//.loginPage("/login/form")
				.loginPage("/login.html")
				// #9
				.loginProcessingUrl("/login").failureUrl("/login/form?error")
				//.loginProcessingUrl("/j_spring_security_check").failureUrl("/login/form?error")
				.permitAll(); // #5
	}
}