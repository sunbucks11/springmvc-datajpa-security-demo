package com.java.blog.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.blog.entity.TestUser;
import com.java.blog.service.TestUserService;


@Service("userServiceTest")
@Transactional
public class TestUserServiceImpl implements TestUserService{
	
	private static final AtomicLong counter = new AtomicLong();
	
	private static List<TestUser> users;
	
	static{
		users= populateDummyUsers();
	}

	public List<TestUser> findAllUsers() {
		return users;
	}
	
	public TestUser findById(long id) {
		for(TestUser user : users){
			if(user.getId() == id){
				return user;
			}
		}
		return null;
	}
	
	public TestUser findByName(String name) {
		for(TestUser user : users){
			if(user.getUsername().equalsIgnoreCase(name)){
				return user;
			}
		}
		return null;
	}
	
	public void saveUser(TestUser user) {
		user.setId(counter.incrementAndGet());
		users.add(user);
	}

	public void updateUser(TestUser user) {
		int index = users.indexOf(user);
		users.set(index, user);
	}

	public void deleteUserById(long id) {
		
		for (Iterator<TestUser> iterator = users.iterator(); iterator.hasNext(); ) {
		    TestUser user = iterator.next();
		    if (user.getId() == id) {
		        iterator.remove();
		    }
		}
	}

	public boolean isUserExist(TestUser user) {
		return findByName(user.getUsername())!=null;
	}
	
	public void deleteAllUsers(){
		users.clear();
	}

	private static List<TestUser> populateDummyUsers(){
		List<TestUser> users = new ArrayList<TestUser>();
		users.add(new TestUser(counter.incrementAndGet(),"Sam", "NY", "sam@abc.com"));
		users.add(new TestUser(counter.incrementAndGet(),"Tomy", "ALBAMA", "tomy@abc.com"));
		users.add(new TestUser(counter.incrementAndGet(),"Kelly", "NEBRASKA", "kelly@abc.com"));
		return users;
	}

}
