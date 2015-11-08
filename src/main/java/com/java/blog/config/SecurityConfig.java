//Source: http://websystique.com/spring-security/spring-security-4-password-encoder-bcrypt-example-with-hibernate/

/**
 * 
 */
package com.java.blog.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;

import com.allanditzel.springframework.security.web.csrf.CsrfTokenResponseHeaderBindingFilter;


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
	@Qualifier("customUserDetailsService")
	UserDetailsService userDetailsService;
	
    
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	    authenticationProvider.setUserDetailsService(userDetailsService);
	    authenticationProvider.setPasswordEncoder(passwordEncoder());
	    return authenticationProvider;
	}
      
   
   
//	@Override
//	protected void configure(AuthenticationManagerBuilder registry)
//			throws Exception {
//		/*
//		 * registry .inMemoryAuthentication() .withUser("siva") // #1
//		 * .password("siva") .roles("USER") .and() .withUser("admin") // #2
//		 * .password("admin") .roles("ADMIN","USER");
//		 */
//
//		// registry.jdbcAuthentication().dataSource(dataSource);
//		
//		registry.jdbcAuthentication().dataSource(dataSource)
//		.passwordEncoder(passwordEncoder())
//		.usersByUsernameQuery("select email, password, true from users where email = ?")	
//		.authoritiesByUsernameQuery("select u.email, r.role_name from users u, roles r where u.email = ? and u.id = r.user_id");
//		
//		registry.userDetailsService(customUserDetailsService);
//	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**"); // #3
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {


		http.csrf().disable()
			.authorizeRequests()
				.antMatchers("/login",  
						"/register", 
						"/index",
						"/error",
						"/reset",
						"/user-account",
						"/user-detail",
						"/user-register",
						"/users",
						"/verification",
						"/HelloWorldRestController",
						"/j_spring_security_check", 
						"/springmvc-datajpa-security-demo/TwoFactorAuthController",
						"/springmvc-datajpa-security-demo/ResetController",
						"/resources/**",
						"/static/**"
						)
				.permitAll()
				
								// #4
				.antMatchers("/admin", "/admin/**").hasRole("ADMIN")
				.antMatchers("/admin/**","/newuser").access("hasRole('ADMIN')")
			  	.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
				// #6
				.anyRequest().authenticated()
				// 7
		
		.and()
			.formLogin()
				// #8
				//.loginPage("/login/form")
				.loginPage("/login.html")
				// #9
				.loginProcessingUrl("/login")
				//.failureUrl("/login/form?error")
//				.failureUrl("/j_spring_security_check")
				//.loginProcessingUrl("/j_spring_security_check").failureUrl("/login/form?error")
				.permitAll() // #5
		.and()
			    .logout()
			    .logoutSuccessUrl("/");
        
	}

}