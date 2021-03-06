package com.bridgelabz.onlinebookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.dto.UserDTO;
import com.bridgelabz.onlinebookstore.exception.UserException;
import com.bridgelabz.onlinebookstore.model.User;

public interface IUserService extends UserDetailsService {
	
	ResponseDTO registerUser(UserDTO userDTO) throws UserException;
	
	ResponseDTO verifyUser(String token) throws UserException;
	
	ResponseDTO userLogin(UserDTO userDTO) throws UserException;
	
	 ResponseDTO forgetPassword(String email) throws UserException;

	Optional<User> getUserByEmail(String email);

	List<User> getAllUser();

	ResponseDTO updateUser(String email, UserDTO userDTO) throws UserException;

	ResponseDTO deleteUser(String email) throws UserException;

	ResponseDTO resetPassword(String token, UserDTO userDTO) throws UserException;
}
