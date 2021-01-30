package com.bridgelabz.onlinebookstore.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.ToString;

public @ToString @Data class UserDTO {
	
	@NotEmpty(message = "First Name cannot be Empty")
	@Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message = "User name Invalid")
	public String firstName;
	 
	@NotEmpty(message = "Last Name cannot be Empty")
	@Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message = "User name Invalid")
	public String lastName;
	
	@NotEmpty(message = "Email cannot be Empty")
	@Email(message = "Enter a valid email address")
	public String email;
	
	@Pattern(regexp="(^$|[0-9]{10})", message = "Mobile number must be 10")
	public String phoneNumber;
	public String address;
	
	@NotEmpty(message = "Enter your password")
	@Length(min = 8, message = "Password must be at least 8 characters")
	public String password;
	
	public String type;

}
