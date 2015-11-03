package com.java.blog.service;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.blog.entity.Blog;
import com.java.blog.entity.Role;
import com.java.blog.entity.User;
import com.java.blog.repository.BlogRepository;
import com.java.blog.repository.RoleRepository;
import com.java.blog.repository.UserRepository;

@Transactional
@Service
public class InitDbService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BlogRepository blogRepository;

	@PostConstruct
	public void init() {
		
		if (roleRepository.findByName("ROLE_ADMIN") == null) {
			Role roleUser = new Role();
			roleUser.setRoleName("ROLE_USER");
			roleRepository.save(roleUser);

		
			Role roleAdmin = new Role();
			//roleAdmin.setName("ROLE_ADMIN");
			roleAdmin.setRoleName("ROLE_ADMIN");
			roleRepository.save(roleAdmin);

			
			User userAdmin = new User();
			userAdmin.setEnabled(true);
			//userAdmin.setName("admin");
			//userAdmin.setEmail("admin@test.com");
			userAdmin.setName("Administrator");
			userAdmin.setEmail("admin@gmail.com");
			
			userAdmin.setResetTwoFactorAuth(false);
			userAdmin.setTwoFactorAuthInitialised(false);
			userAdmin.setVerified(false);
			userAdmin.setVerifiedError(false);
			userAdmin.setAuthenticated(false);
			
			
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			userAdmin.setPassword(encoder.encode("admin"));
			//userAdmin.setPassword(encoder.encode("admin123"));
			//userAdmin.setPassword("admin");
			
			//List<Role> roles = new ArrayList<Role>();
			Set<Role> roles = new HashSet<>();
			roles.add(roleAdmin);
			roles.add(roleUser);
			userAdmin.setRoles(roles);
			userRepository.save(userAdmin);

			Blog blogJavavids = new Blog();
			blogJavavids.setName("JavaVids");
			blogJavavids.setUrl("http://feeds.feedburner.com/javavids?format=xml");
			blogJavavids.setUser(userAdmin);
			blogRepository.save(blogJavavids);
			

			/*
			Item item1 = new Item();
			item1.setBlog(blogJavavids);
			item1.setTitle("first");
			item1.setLink("http://www.javavids.com");
			item1.setPublishedDate(new Date());
			itemRepository.save(item1);
			
			Item item2 = new Item();
			item2.setBlog(blogJavavids);
			item2.setTitle("second");
			item2.setLink("http://www.javavids.com");
			item2.setPublishedDate(new Date());
			itemRepository.save(item2);
		*/
			}
		
		}
	
	//}

}
