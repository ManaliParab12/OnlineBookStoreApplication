package com.bridgelabz.onlinebookstore.service;

import java.util.List;
import java.util.Optional;

import com.bridgelabz.onlinebookstore.dto.UserDTO;
import com.bridgelabz.onlinebookstore.model.User;

public interface IUserService {
	
	User registerUser(UserDTO userDTO);

	Optional<User> getUserByEmail(String email);

	List<User> getAllUser();

}
