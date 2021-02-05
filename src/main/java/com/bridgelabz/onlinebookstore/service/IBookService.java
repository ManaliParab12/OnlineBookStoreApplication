package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.exception.UserException;

public interface IBookService {
	
	ResponseDTO addBook(String token, BookDTO bookDTO) throws UserException;

}
