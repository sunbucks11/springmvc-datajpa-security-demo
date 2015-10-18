/**
 * 
 */
package com.java.blog.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.java.blog.annotation.UniqueUsername;

@Entity
@Table(name="USERS")
public class User 
{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Size(min=3, message="Name must be at least 3 characters!")
	@Column(unique = true)
	@UniqueUsername(message="Such username already exists!")
	private String name;
	
	@Column(nullable=false, unique=true)
	private String email;
	
	@Column(nullable=false)
	@Size(min=5, message="Password must be at least 5 characters!")
	private String password;
	
	//@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="user_id")
	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable
	//private Set<Role> roles = new HashSet<>();
	private List<Role> roles;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<Blog> blogs;
		
	private Date dob;
	
	
	private String secretKey;
	private Boolean twoFactorAuthInitialised;
	private boolean isAuthenticated; 
	private boolean isVerified; 
	private boolean isVerifiedError; 
	private boolean isResetTwoFactorAuth;
	private boolean enabled;
	
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

	public boolean isResetTwoFactorAuth() {
		return isResetTwoFactorAuth;
	}

	public void setResetTwoFactorAuth(boolean isResetTwoFactorAuth) {
		this.isResetTwoFactorAuth = isResetTwoFactorAuth;
	}
	
	
	public User() {
	}

	public User(int id, String name, String email, String password, Date dob) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.dob = dob;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email
				+ ", dob=" + dob + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

/*	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}*/
	
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Role> getRoles() {
		return roles;
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
	
	public List<Blog> getBlogs() {
		return blogs;
	}

	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}