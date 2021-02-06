package com.bridgelabz.onlinebookstore.service;

import java.util.List;

import com.bridgelabz.onlinebookstore.dto.CartDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.exception.UserException;
import com.bridgelabz.onlinebookstore.model.Cart;

public interface ICartService {
	
	ResponseDTO addBookToCart(CartDTO cartDTO);
	
	ResponseDTO  updateCart(String token, int bookId, int quantity) throws UserException;
	
	List<Cart> getListOfBooksInCart(String token);



}
