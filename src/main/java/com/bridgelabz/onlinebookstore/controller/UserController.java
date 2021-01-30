package com.bridgelabz.onlinebookstore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.dto.UserDTO;
import com.bridgelabz.onlinebookstore.model.User;
import com.bridgelabz.onlinebookstore.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<ResponseDTO> registerUser( @Valid @RequestBody UserDTO userDTO) {
		User user = null;
		user = userService.registerUser(userDTO);
		ResponseDTO responseDTO = new ResponseDTO("Get Call Successful", user);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);		
	}
}
