package com.bridgelabz.onlinebookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.exception.UserException;
import com.bridgelabz.onlinebookstore.model.Book;
import com.bridgelabz.onlinebookstore.model.User;
import com.bridgelabz.onlinebookstore.repository.BookRepository;
import com.bridgelabz.onlinebookstore.repository.UserRepository;
import com.bridgelabz.onlinebookstore.utility.Token;

@Service
public class BookService implements IBookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public ResponseDTO addBook(String token, BookDTO bookDTO) throws UserException {
		int id = Token.decodeToken(token);
		User user = userRepository.findById(id)
				.orElseThrow(() ->  new UserException("User Not Found"));
		Book book = new Book(bookDTO);
		if(user.getType().equalsIgnoreCase("admin")) {
			bookRepository.save(book);
			return new ResponseDTO("Book Added Successfully");
		} else {
			return new ResponseDTO("Action not allowed");	
		}	
	}
	
	@Override
	public List<Book> getAllBooks() {
		List<Book> books = bookRepository.findAll();
		return books;
	}

}
