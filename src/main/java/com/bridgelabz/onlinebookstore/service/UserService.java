package com.bridgelabz.onlinebookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.dto.UserDTO;
import com.bridgelabz.onlinebookstore.exception.UserException;
import com.bridgelabz.onlinebookstore.model.User;
import com.bridgelabz.onlinebookstore.repository.UserRepository;
import com.bridgelabz.onlinebookstore.utility.Token;

@Service
public class UserService implements IUserService {
	
	 @Autowired
	 private UserRepository userRepository;
	 
	 @Autowired
	 private IEmailService emailService;
	 
	 @Autowired
	 private BCryptPasswordEncoder bCryptPasswordEncoder;
	 
	 public ResponseDTO registerUser(UserDTO userDTO) throws UserException {
		 String password = bCryptPasswordEncoder.encode(userDTO.getPassword());
		 User user = new User(userDTO);
	     if(userRepository.findByEmail(user.getEmail()).isPresent())
	    	 throw new UserException("User is Already Registered with this Email Id");
	     user.setPassword(password);
	     userRepository.save(user);
	     emailService.sendVerificationMail(user);
	     return new ResponseDTO("User Registered successfully");
	}
	 
	 
	@Override
	public ResponseDTO verifyUser(String token) {
		int userId = Token.decodeToken(token);
		User user = userRepository.findById(userId).get();
		user.setVerify(true);
		userRepository.save(user);
		return ResponseDTO.getResponse("User Verified Successfully", user);
	}
	
	@Override
	public ResponseDTO userLogin(UserDTO userDTO) throws UserException {
		User user = userRepository.findByEmail(userDTO.getEmail())
				.orElseThrow(() -> new UserException("User Not Found"));
		//String token = Token.generateToken(user.getId());
		if(bCryptPasswordEncoder.matches(userDTO.getPassword(), user.getPassword()) && user.isVerify())
			return new ResponseDTO("login Successfull");
		throw new UserException("User not Verified");
	}
	
	 
	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public ResponseDTO updateUser(String email, UserDTO userDTO) {
		 User user = userRepository.findByEmail(email).get();
		 user.updateUser(userDTO);
		 return ResponseDTO.getResponse("User Details updated", user);
	}
	
	@Override
    public ResponseDTO deleteUser(String email) {
        User user = this.getUserByEmail(email).get();
        userRepository.delete(user);
        return ResponseDTO.getResponse("User Deleted", user);
    }	
}
