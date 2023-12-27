package com.authservice.entity;

import jakarta.persistence.*;

@Entity
public class ApplicationUser {

	//@Id
	//@Column(name = "UserId")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//private int userid;
	
	@Id
	//@Column(name = "Username")
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private String username;
	
	
	//@Column(name = "Password")
	private String password;

	private String role;

	/*
	 * public int getUserid() { return userid; }
	 * 
	 * public void setUserid(int userid) { this.userid = userid; }
	 */

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
