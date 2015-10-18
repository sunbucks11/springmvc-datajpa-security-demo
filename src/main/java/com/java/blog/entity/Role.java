/**
 * 
 */
package com.java.blog.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author Siva
 *
 */


@Entity
@Table(name = "ROLES")
public class Role implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Integer id;

	@Column(name = "role_name", nullable = false)
	private String roleName;

	@ManyToMany(mappedBy = "roles")
	private List<User> users;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return roleName;
	}

	public void setName(String name) {
		this.roleName = name;
	}
	
	
	
	
	
	/*
	public Role() {
	}
	
	public Role(String roleName) {
		this.roleName = roleName;
	}

	public Role(Integer id, String roleName) {
		this.id = id;
		this.roleName = roleName;
	}
	*/
	
	
}



/*
@Entity
@Table(name = "ROLES")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Integer id;
	
	@Column(name = "role_name", nullable = false)
	private String roleName;
	
	
	@ManyToMany(mappedBy = "roles")
	private List<User> users;
	

	public Role() {
	}

	public Role(String roleName) {
		this.roleName = roleName;
	}

	public Role(Integer id, String roleName) {
		this.id = id;
		this.roleName = roleName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
	
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
*/