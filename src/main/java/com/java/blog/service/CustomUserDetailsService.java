package com.java.blog.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.blog.entity.Role;
import com.java.blog.entity.User;
import com.java.blog.entity.UserProfile;

/*import com.websystique.springsecurity.model.User;
import com.websystique.springsecurity.model.UserProfile;*/

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserService userService;
	
//	 @Autowired
//	 UserProfileService userProfileService;
	 
	
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		User user = userService.findUserByEmail(email);
		System.out.println("User : "+user);
		if(user==null){
			System.out.println("User not found");
			throw new UsernameNotFoundException("Username not found"); 
		}
//			return new org.springframework.security.core.userdetails.User(user.getId().toString(), user.getPassword(), 
//				 user.getState().equals("Active"), true, true, true, getGrantedAuthorities(user));

		
		List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());
		
		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), 
				 user.getState().equals("Active"), true, true, true, authorities);
	}

	
	
	private List<GrantedAuthority> buildUserAuthority(Set<Role> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		for (Role userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRoleName()));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}
}
