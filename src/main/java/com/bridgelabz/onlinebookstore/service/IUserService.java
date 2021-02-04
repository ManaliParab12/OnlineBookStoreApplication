package com.bridgelabz.onlinebookstore.service;

import java.util.List;
import java.util.Optional;

import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.dto.UserDTO;
import com.bridgelabz.onlinebookstore.exception.UserException;
import com.bridgelabz.onlinebookstore.model.User;

public interface IUserService {
	
	ResponseDTO registerUser(UserDTO userDTO) throws UserException;
	
	ResponseDTO verifyUser(String token);
	
	ResponseDTO userLogin(UserDTO userDTO) throws UserException;
	
	 ResponseDTO forgetPassword(String email);

	Optional<User> getUserByEmail(String email);

	List<User> getAllUser();

	ResponseDTO updateUser(String email, UserDTO userDTO);

	ResponseDTO deleteUser(String email);


}
