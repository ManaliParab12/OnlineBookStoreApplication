package com.bridgelabz.onlinebookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.dto.UserDTO;
import com.bridgelabz.onlinebookstore.exception.UserException;
import com.bridgelabz.onlinebookstore.model.User;
import com.bridgelabz.onlinebookstore.repository.UserRepository;

@Service
public class UserService implements IUserService {
	
	 @Autowired
	 private UserRepository userRepository;
	 
	 public User registerUser(UserDTO userDTO) throws UserException {
		 User user = null;
	     user = new User(userDTO);
	     if(userRepository.findByEmail(user.getEmail()).isPresent())
	    	 throw new UserException("User is Already Registered with this Email Id");
	     user = userRepository.save(user);
	     return user;
	}

	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public User updateUser(String email, UserDTO userDTO) {
		 User user = userRepository.findByEmail(email).get();
		 user.updateUser(userDTO);
	     return userRepository.save(user);
	}
	
	@Override
    public User deleteUser(String email) {
        User user = this.getUserByEmail(email).get();
        userRepository.delete(user);
        return user;
    }
	
}
