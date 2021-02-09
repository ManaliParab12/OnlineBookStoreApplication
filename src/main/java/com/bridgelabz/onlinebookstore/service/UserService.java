package com.bridgelabz.onlinebookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.dto.UserDTO;
import com.bridgelabz.onlinebookstore.exception.UserException;
import com.bridgelabz.onlinebookstore.model.User;
import com.bridgelabz.onlinebookstore.repository.UserRepository;
import com.bridgelabz.onlinebookstore.utility.Token;

@Service
@PropertySource("classpath:status.properties")
public class UserService implements IUserService {
	
	@Autowired
	private Environment environment;
	
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
		return new ResponseDTO("Registration successful, verification link has been sent to your email id:" , user.getEmail());
	}
	
	@Override
	public ResponseDTO verifyUser(String token) throws UserException {
		int userId = Token.decodeToken(token);
		User user = userRepository.findById(userId)
				  				  .orElseThrow(() -> new UserException(environment.getProperty("status.login.error.message")));
		user.setVerify(true);
		userRepository.save(user);
		return ResponseDTO.getResponse("User Verified Successfully", user);
	}
	
	@Override
	public ResponseDTO userLogin(UserDTO userDTO) throws UserException {
		User user = userRepository.findByEmail(userDTO.getEmail())
								  .orElseThrow(() -> new UserException(environment.getProperty("status.login.error.message")));
		if(bCryptPasswordEncoder.matches(userDTO.getPassword(), user.getPassword()) && user.isVerify())
			return new ResponseDTO("login Successfull");
		throw new UserException("Identity Verification, Action Required!");
	}
	
	 
	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public ResponseDTO updateUser(String email, UserDTO userDTO) throws UserException {
		 User user = userRepository.findByEmail(email)
				 				   .orElseThrow(() -> new UserException(environment.getProperty("status.login.error.message")));
		 user.updateUser(userDTO);
		 userRepository.save(user);
		 return ResponseDTO.getResponse("Record updated successfully", user);
	}
	
	@Override
	public ResponseDTO forgetPassword(String email) throws UserException {
		User user = userRepository.findByEmail(email)
								  .orElseThrow(() -> new UserException(environment.getProperty("status.login.error.message")));
		emailService.sendResetPasswordMail(user);
		return new ResponseDTO("Reset Password link has been sent to your registered email id : " +user.getEmail() );
	}
	
	@Override
	public ResponseDTO resetPassword(String token, UserDTO userDTO) throws UserException {
		String password = bCryptPasswordEncoder.encode(userDTO.getPassword());
		int userId = Token.decodeToken(token);
		User user = userRepository.findById(userId)
				 				  .orElseThrow(() -> new UserException(environment.getProperty("status.login.error.message")));
		user.setPassword(password);
		userRepository.save(user);
		return ResponseDTO.getResponse("Your password has been changed successfully!", user);
	}
	
	
	@Override
    public ResponseDTO deleteUser(String email) throws UserException {
        User user = this.getUserByEmail(email)
        		 		.orElseThrow(() -> new UserException(environment.getProperty("status.login.error.message")));
        userRepository.delete(user);
        return ResponseDTO.getResponse("Record has been successfully deleted", user);
    }	
}
