package com.bridgelabz.onlinebookstore.model;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bridgelabz.onlinebookstore.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Role> role;

	public User() {}	

	public User(UserDTO userDTO,  List<Role> role) {
		this.updateUser(userDTO);
		this.role = role;
	}
	public User(UserDTO userDTO) {
		this.updateUser(userDTO);
	}

	public void updateUser(UserDTO userDTO) {
		 this.firstName = userDTO.firstName;
		 this.lastName = userDTO.lastName;
		 this.email = userDTO.email;
		 this.phoneNumber = userDTO.phoneNumber;
		 this.address = userDTO.address;
		 this.password = userDTO.password;		
	}
	

	public User(String firstName, String lastName, String email, String phoneNumber, String address,
			String password, boolean isVerify, List<Role> role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.password = password;
		this.isVerify = isVerify;
		this.role = role;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (isVerify != other.isVerify)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		return true;
	}


}
