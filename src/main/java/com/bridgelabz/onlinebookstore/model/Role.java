package com.bridgelabz.onlinebookstore.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "role")
public @Data  class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_id")
	private int id;
	
	private String role;
	
	@ManyToMany(mappedBy="role")
	private List<User> user;

	public Role() {}

	public Role(String role) {
		this.role = role;
	}
	
	
}
