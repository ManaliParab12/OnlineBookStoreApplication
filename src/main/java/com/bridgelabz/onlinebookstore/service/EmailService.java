package com.bridgelabz.onlinebookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.bridgelabz.onlinebookstore.dto.EmailDTO;

public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;	
	
	public void sendRegistrationMail(EmailDTO emailDTO) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(emailDTO.getEmail());
		mailMessage.setSubject(emailDTO.getSubject());
		mailMessage.setText(emailDTO.getMessage());
		mailSender.send(mailMessage);		
	}
}
