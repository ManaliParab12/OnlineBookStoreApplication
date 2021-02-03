package com.bridgelabz.onlinebookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.model.User;
import com.bridgelabz.onlinebookstore.utility.Token;

@Service
public class EmailService implements IEmailService {
	
	@Autowired
	JavaMailSender mailSender;	
	
	public void sendMail(User user) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail());
		mailMessage.setSubject("Verification Link");
		mailMessage.setText("Thank you for registration");
		mailSender.send(mailMessage);		
	}
	
	@Override
	public ResponseDTO RegistrationMail(User user) {
		String token = Token.generateToken(user.getId());
		this.sendMail(user);
		return ResponseDTO.getResponse("verification mail sent",token);
	}
}
