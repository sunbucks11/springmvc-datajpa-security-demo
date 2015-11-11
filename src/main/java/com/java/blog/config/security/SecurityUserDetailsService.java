package com.java.blog.config.security;

import com.java.blog.repository.spa.UserRepositoryTest;
import com.java.blog.service.UserService;

//import calories.tracker.app.dao.UserRepository;
//import calories.tracker.app.security.SecurityUserDetailsService;

import com.java.blog.entity.Role;
import com.java.blog.entity.User;
import com.java.blog.entity.spa.UserTest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = Logger.getLogger(SecurityUserDetailsService.class);

    @Autowired
    private UserRepositoryTest userRepository;
    
    @Autowired
	private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
    {
    	
    	if(username.contains("@"))
    	{
    		User user = userService.findUserByEmail(username);
    		
    		System.out.println("User : "+user);
    		if(user==null){
    			System.out.println("User not found");
    			throw new UsernameNotFoundException("Username not found"); 
    		}
    		
    		List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());
    		
    		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), 
    				 user.getState().equals("Active"), true, true, true, authorities);
    		
    	}
    	else{
    	UserTest user = userRepository.findUserByUsername(username);

        if (user == null) {
            String message = "Username not found" + username;
            LOGGER.info(message);
            throw new UsernameNotFoundException(message);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        LOGGER.info("Found user in database: " + user);

        return new org.springframework.security.core.userdetails.User(username, user.getPasswordDigest(), authorities);
    	}
    }
    
    
    
    
	private List<GrantedAuthority> buildUserAuthority(Set<Role> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		for (Role userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRoleName()));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}
}


/*
//@Service("customUserDetailsService")
@Service
public class SecurityUserDetailsService implements UserDetailsService {

	
    private static final Logger LOGGER = Logger.getLogger(SecurityUserDetailsService.class);

    @Autowired
    private UserRepositoryTest userRepository;
    
    @Autowired
	private UserService userService;
    

    @Override
    //@Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
    	
    	if(username.contains("@"))
    	{
    		User user = userService.findUserByEmail(username);
    		
    		System.out.println("User : "+user);
    		if(user==null){
    			System.out.println("User not found");
    			throw new UsernameNotFoundException("Username not found"); 
    		}
    		
    		List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());
    		
    		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), 
    				 user.getState().equals("Active"), true, true, true, authorities);
    		
    	}
    	else{
    	
    	
    	UserTest user = userRepository.findUserByUsername(username);

        if (user == null) {
            String message = "Username not found" + username;
            LOGGER.info(message);
            throw new UsernameNotFoundException(message);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        LOGGER.info("Found user in database: " + user);

        return new org.springframework.security.core.userdetails.User(username, user.getPasswordDigest(), authorities);
       }
    }
    
	private List<GrantedAuthority> buildUserAuthority(Set<Role> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		for (Role userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRoleName()));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}
}
*/



/*
@Service
public class SecurityUserDetailsService implements UserDetailsService {

	
    private static final Logger LOGGER = Logger.getLogger(SecurityUserDetailsService.class);

    @Autowired
    private UserRepositoryTest userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserTest user = userRepository.findUserByUsername(username);

        if (user == null) {
            String message = "Username not found" + username;
            LOGGER.info(message);
            throw new UsernameNotFoundException(message);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        LOGGER.info("Found user in database: " + user);

        return new org.springframework.security.core.userdetails.User(username, user.getPasswordDigest(), authorities);
    }
}
*/