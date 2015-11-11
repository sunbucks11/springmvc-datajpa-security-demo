//Source: http://websystique.com/spring-security/spring-security-4-password-encoder-bcrypt-example-with-hibernate/


package com.java.blog.config.security;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
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

import com.java.blog.config.security.SecurityUserDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = Logger.getLogger(SecurityConfig.class);

    @Autowired
    private SecurityUserDetailsService userDetailsService;

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

       CsrfTokenResponseHeaderBindingFilter csrfTokenFilter = new CsrfTokenResponseHeaderBindingFilter();
        http.addFilterAfter(csrfTokenFilter, CsrfFilter.class);

        http
            .authorizeRequests()
            .antMatchers("/resources/public/**").permitAll()
            .antMatchers("/resources/img/**").permitAll()
            .antMatchers("/resources/bower_components/**").permitAll()
            .antMatchers(HttpMethod.POST, "/user").permitAll()
            .anyRequest().authenticated()
            .and()
       .formLogin()
            .defaultSuccessUrl("/resources/calories-tracker.html")
            .loginProcessingUrl("/authenticate")
            .usernameParameter("username")
            .passwordParameter("password")
            .successHandler(new AjaxAuthenticationSuccessHandler(new SavedRequestAwareAuthenticationSuccessHandler()))
            .loginPage("/resources/public/login.html")
            .and()
            .httpBasic()
       .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/resources/public/login.html")
            .permitAll();

        if ("true".equals(System.getProperty("httpsOnly"))) {
            LOGGER.info("launching the application in HTTPS-only mode");
            http.requiresChannel().anyRequest().requiresSecure();
        }
    }
    
	
	
	
	
	
 /*
	@Autowired
	private DataSource dataSource;
	
    @Autowired
    private SecurityUserDetailsService userDetailsService;
	    
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
      
   
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**"); // #3
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//################## Test #################
        // CsrfTokenResponseHeaderBindingFilter csrfTokenFilter = new CsrfTokenResponseHeaderBindingFilter();
        // http.addFilterAfter(csrfTokenFilter, CsrfFilter.class);
		//#########################################

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
						"/springmvc-datajpa-security-demo/ResetController"
						).permitAll()			
				.antMatchers("/admin", "/admin/**").hasRole("ADMIN")
				.antMatchers("/admin/**","/newuser").access("hasRole('ADMIN')")
			  	.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
				//.anyRequest().authenticated()	
				
			  	//########## Test ###################
	            .antMatchers("/resources/public/**").permitAll()
	            .antMatchers("/resources/img/**").permitAll()
	            .antMatchers("/resources/bower_components/**").permitAll()
	            .antMatchers(HttpMethod.POST, "/user").permitAll()
	            .anyRequest().authenticated()
				//##################################
		.and()
			.formLogin() 
				//.loginPage("/login.html")
				//.loginProcessingUrl("/login")
				//.permitAll()
				//############ Test #################
	            .defaultSuccessUrl("/resources/calories-tracker.html")
	            .loginProcessingUrl("/authenticate")
	            //.loginProcessingUrl("/login")
	            .usernameParameter("username")
	            .passwordParameter("password")
	            .successHandler(new AjaxAuthenticationSuccessHandler(new SavedRequestAwareAuthenticationSuccessHandler()))
	            .loginPage("/resources/public/login.html")
	            .and()
	            .httpBasic()
				//###################################	
		.and()		   
				//.logout()
			    //.logoutSuccessUrl("/"); 
				//########### Test ############
		        .logout()
		        .logoutUrl("/logout")
		        .logoutSuccessUrl("/resources/public/login.html")
		        .permitAll();
				//#############################
		
        if ("true".equals(System.getProperty("httpsOnly"))) {
           // LOGGER.info("launching the application in HTTPS-only mode");
            http.requiresChannel().anyRequest().requiresSecure();
        }
	}
	*/
    
    
}



















/*
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	
    private static final Logger LOGGER = Logger.getLogger(SecurityConfig.class);

 //   @Autowired
//	@Qualifier("customUserDetailsService")
//    private UserDetailsService userDetailsService;
    
    @Autowired
    private SecurityUserDetailsService userDetailsService;

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CsrfTokenResponseHeaderBindingFilter csrfTokenFilter = new CsrfTokenResponseHeaderBindingFilter();
        http.addFilterAfter(csrfTokenFilter, CsrfFilter.class);

        http
        .authorizeRequests()
        .antMatchers("/resources/public/**").permitAll()
        .antMatchers("/resources/img/**").permitAll()
        .antMatchers("/resources/bower_components/**").permitAll()
        .antMatchers(HttpMethod.POST, "/user").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .defaultSuccessUrl("/resources/calories-tracker.html")
        .loginProcessingUrl("/authenticate")
        .usernameParameter("username")
        .passwordParameter("password")
        .successHandler(new AjaxAuthenticationSuccessHandler(new SavedRequestAwareAuthenticationSuccessHandler()))
        .loginPage("/resources/public/login.html")
        .and()
        .httpBasic()
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/resources/public/login.html")
        .permitAll();

        if ("true".equals(System.getProperty("httpsOnly"))) {
            LOGGER.info("launching the application in HTTPS-only mode");
            http.requiresChannel().anyRequest().requiresSecure();
        }
    }
*/	
	
	    
	    
	/*    
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
				
				.antMatchers("/admin", "/admin/**").hasRole("ADMIN")
				.antMatchers("/admin/**","/newuser").access("hasRole('ADMIN')")
			  	.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
				.anyRequest().authenticated()	
		.and()
			.formLogin()
				.loginPage("/login.html")
				.loginProcessingUrl("/login")
				.permitAll()
		.and()
			    .logout()
			    .logoutSuccessUrl("/");      
	}
	
*/