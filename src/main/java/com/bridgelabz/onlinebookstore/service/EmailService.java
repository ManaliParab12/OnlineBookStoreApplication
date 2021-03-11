package com.bridgelabz.onlinebookstore.service;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.dto.EmailDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.model.User;
import com.bridgelabz.onlinebookstore.utility.Token;
import com.google.gson.Gson;

@Service
public class EmailService implements IEmailService {
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	Gson gson;
	
	@Autowired
	Queue queue;
	
	 @Autowired
	 private RabbitTemplate rabbitTemplate;
	 
	 @Autowired
	 private Binding bind;
	
	@RabbitListener(queues = "${rabbitmq.queue}")
	public void sendMail(String email) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		EmailDTO emailDTO = gson.fromJson(email, EmailDTO.class);
		mailMessage.setTo(emailDTO.getEmail());
		mailMessage.setSubject(emailDTO.getSubject());
		mailMessage.setText(emailDTO.getMessage());
		mailSender.send(mailMessage);		
	}
	
	@Override
	public ResponseDTO sendVerificationMail(User user) {
		String token = Token.generateToken(user.getId());
		rabbitTemplate.convertAndSend(bind.getExchange(), bind.getRoutingKey(),
					   gson.toJson(new EmailDTO(user.getEmail(), "verification Link ", verificationURL(token))));
//		this.sendMail(new EmailDTO(user.getEmail(), "verification Link ", verificationURL(token)));
		return new ResponseDTO("verification link has been sent to your registered email id : " +user.getEmail());
	}
	
	private String verificationURL(String token) {
		System.out.println("Token : " +token);
		return "Click on below link to verify \n" +
				"http://localhost:8081/swagger-ui.html#!/user-controller/verifyUserUsingGET" +
				"\n Token : " +token;
	}
	
	@Override
	public ResponseDTO sendResetPasswordMail(User user) {
		String token = Token.generateToken(user.getId());
		rabbitTemplate.convertAndSend(bind.getExchange(), bind.getRoutingKey(),
					   gson.toJson(new EmailDTO(user.getEmail(), "Reset Password Link ",  resetURL(token))));
//		this.sendMail(new EmailDTO(user.getEmail(), "Reset Password Link", resetURL(token)));
		return new ResponseDTO("Reset Password link has been sent to your registered email id : " +user.getEmail());
	}
	
	private String resetURL(String token) {
		return "Click on below link to Reset your Password \n" +
				"http://localhost:8081/swagger-ui.html#!/user-controller/resetPasswordUsingPOST" +
				"\n Token : " +token;				
	}
}
