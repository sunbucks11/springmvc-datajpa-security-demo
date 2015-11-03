/**
 * 
 */
package com.java.blog.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.java.blog.annotation.UniqueUsername;
//import com.websystique.springsecurity.model.UserProfile;



@Entity
@Table(name="USERS")
public class User 
{
	@Id
	@GeneratedValue
	private Integer id;

	@Size(min = 3, message = "Name must be at least 3 characters!")
	@Column(unique = true)
	@UniqueUsername(message = "Such username already exists!")
	private String name;

	@Size(min = 1, message = "Invalid email address!")
	@Email(message = "Invalid email address!")
	private String email;

	@Size(min = 5, message = "Name must be at least 5 characters!")
	private String password;

	private boolean enabled;
	
	private Date dob;

	private String secretKey;
	private boolean twoFactorAuthInitialised;
	private boolean isResetTwoFactorAuth;
	private boolean isAuthenticated; 
	private boolean isVerified; 
	private boolean isVerifiedError; 

	
	@NotEmpty
	@Column(name="STATE", nullable=false)
	private String state=State.ACTIVE.getState();
	
	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "APP_USER_USER_PROFILE", 
             joinColumns = { @JoinColumn(name = "USER_ID") }, 
             inverseJoinColumns = { @JoinColumn(name = "USER_PROFILE_ID") })
	private Set<UserProfile> userProfiles = new HashSet<UserProfile>();
	
	

	public Set<UserProfile> getUserProfiles() {
		return userProfiles;
	}

	public void setUserProfiles(Set<UserProfile> userProfiles) {
		this.userProfiles = userProfiles;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable
	//private List<Role> roles;
	private Set<Role> roles = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<Blog> blogs;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Blog> getBlogs() {
		return blogs;
	}

	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}

	public Set<Role> getRoles() {
		return roles;
	}
	
	

/*	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}*/
	public void setRoles(Set<Role> set) {
		this.roles = set;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
	public Boolean getTwoFactorAuthInitialised() {
		return twoFactorAuthInitialised;
	}

	public void setTwoFactorAuthInitialised(Boolean twoFactorAuthInitialised) {
		this.twoFactorAuthInitialised = twoFactorAuthInitialised;
	}
	
	public boolean isResetTwoFactorAuth() {
		return isResetTwoFactorAuth;
	}

	public void setResetTwoFactorAuth(boolean isResetTwoFactorAuth) {
		this.isResetTwoFactorAuth = isResetTwoFactorAuth;
	}
	
	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public boolean isVerifiedError() {
		return isVerifiedError;
	}

	public void setVerifiedError(boolean isVerifiedError) {
		this.isVerifiedError = isVerifiedError;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}