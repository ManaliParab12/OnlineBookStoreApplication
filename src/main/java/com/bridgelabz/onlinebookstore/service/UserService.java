package com.bridgelabz.onlinebookstore.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.dto.UserDTO;
import com.bridgelabz.onlinebookstore.model.User;
import com.bridgelabz.onlinebookstore.repository.UserRepository;

@Service
public class UserService implements IUserService {
	
	 @Autowired
	 private UserRepository userRepository;
	 
	 public User registerUser(UserDTO userDTO) {
		 User user = null;
	     user = new User(userDTO);
	     user = userRepository.save(user);
	     return user;
	}

	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}


}
