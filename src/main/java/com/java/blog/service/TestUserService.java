package com.java.blog.service;

import java.util.List;

import com.java.blog.entity.TestUser;



public interface TestUserService {
	
	TestUser findById(long id);
	
	TestUser findByName(String name);
	
	void saveUser(TestUser user);
	
	void updateUser(TestUser user);
	
	void deleteUserById(long id);

	List<TestUser> findAllUsers(); 
	
	void deleteAllUsers();
	
	public boolean isUserExist(TestUser user);
	
}
