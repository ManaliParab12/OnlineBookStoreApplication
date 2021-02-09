package com.bridgelabz.onlinebookstore.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.dto.UserDTO;
import com.bridgelabz.onlinebookstore.exception.UserException;
import com.bridgelabz.onlinebookstore.model.User;
import com.bridgelabz.onlinebookstore.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private IUserService userService;	
	
	@PostMapping("/register")
	public ResponseEntity<ResponseDTO> registerUser(@Valid @RequestBody UserDTO userDTO) throws UserException {
		ResponseDTO responseDTO = null;
		try {
			responseDTO =  userService.registerUser(userDTO);
		} catch (UserException e) {
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.CONFLICT);	
		}
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);	
	}
	
	
	@GetMapping("/verify/{token}")
	public ResponseEntity<ResponseDTO> verifyUser(@RequestHeader ("Token") String token) throws UserException {
        return new ResponseEntity<ResponseDTO>(userService.verifyUser(token), HttpStatus.OK);		
	}
	
	
	@PutMapping("/login")
	public ResponseEntity<ResponseDTO> loginUser(@RequestBody UserDTO userDTO) throws UserException {
		ResponseDTO responseDTO = userService.userLogin(userDTO);
		 return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);		
	}
	
	
	@GetMapping("/Get")
	public ResponseEntity<User> getUser(@RequestParam("email") String email) {
		return new ResponseEntity<>(userService.getUserByEmail(email).get(), HttpStatus.OK);		
	}
	
	
	@GetMapping("/allusers")
	public ResponseEntity<ResponseDTO> getAllUser() {
		 List<User> userList = null;
	     userList = userService.getAllUser();
	     ResponseDTO responseDTO = new ResponseDTO("List of Users", userList);
	     return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	
	@PutMapping("/update/{email}")
	public ResponseEntity<ResponseDTO> updateUser(@Valid @RequestParam ("email") String email, UserDTO userDTO) throws UserException {
		 ResponseDTO responseDTO = userService.updateUser(email, userDTO);
	     return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	
	@PutMapping("/forget-password")
	public ResponseEntity<ResponseDTO> sendResetPasswordMail(@Valid @RequestParam ("email") String email) throws UserException {			
	    ResponseDTO responseDTO = userService.forgetPassword(email);
	    return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);	
	}
	
	
	@PostMapping("/reset-password/{token}")
	public ResponseEntity<ResponseDTO> resetPassword(@RequestHeader ("Token") String token, @RequestBody UserDTO userDTO ) throws UserException {
		return new ResponseEntity<ResponseDTO>(userService.resetPassword(token, userDTO), HttpStatus.OK);
	}
	
	
	@DeleteMapping("/delete/{email}")
	public ResponseEntity<ResponseDTO> deleteUser(@RequestParam ("email") String email) throws UserException {
		ResponseDTO responseDTO = userService.deleteUser(email);
	    return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
}