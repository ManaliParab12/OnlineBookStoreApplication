package com.bridgelabz.onlinebookstore.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name = "role")
@JsonIgnoreProperties(ignoreUnknown = true)
public @Data  class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_id")
	private int id;
	
	private String role;
	
	@JsonIgnore
	@ManyToMany(mappedBy="role")
	private List<User> user;

	public Role() {}

	public Role(String role) {
		this.role = role;
	}
	
	
}
