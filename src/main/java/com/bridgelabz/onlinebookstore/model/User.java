package com.bridgelabz.onlinebookstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "user")
public @Data @ToString class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private int id;
	  
	@Column(name = "first_name")
	private String firstName;
	  
	private String lastName;
	private String email;
	private String phoneNumber;
	private String address;
	private String password;
	private boolean isVerify;
	private String type;

	public User() {}
	
	public User(int id, String firstName, String lastName, String email, String phoneNumber, String address,
			String password, boolean isVerify, String type) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.password = password;
		this.isVerify = isVerify;
		this.type = type;
	}
}
