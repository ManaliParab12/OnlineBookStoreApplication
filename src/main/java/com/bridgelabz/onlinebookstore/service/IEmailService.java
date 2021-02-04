package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.model.User;

public interface IEmailService {

	ResponseDTO sendVerificationMail(User user);
	
	ResponseDTO sendResetPasswordMail(User user);
}
