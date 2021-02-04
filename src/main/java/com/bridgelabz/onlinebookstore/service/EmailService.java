package com.bridgelabz.onlinebookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.dto.EmailDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.model.User;
import com.bridgelabz.onlinebookstore.utility.Token;

@Service
public class EmailService implements IEmailService {
	
	@Autowired
	JavaMailSender mailSender;	
	
	public void sendMail(EmailDTO emailDTO) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(emailDTO.getEmail());
		mailMessage.setSubject(emailDTO.getSubject());
		mailMessage.setText(emailDTO.getMessage());
		mailSender.send(mailMessage);		
	}
	
	@Override
	public ResponseDTO sendVerificationMail(User user) {
		String token = Token.generateToken(user.getId());
		this.sendMail(new EmailDTO(user.getEmail(), "verification Link ", getVerificationURL(token)));
		return new ResponseDTO("verification mail sent");
	}
	
	private String getVerificationURL(String token) {
		System.out.println("Token : " +token);
		return "Click on below link to verify \n" +
				"http://localhost:8080/swagger-ui.html#!/user-controller/verifyUserUsingGET" +
				"\n Token : " +token;
	}

}
