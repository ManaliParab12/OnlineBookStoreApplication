package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.CartDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;

public interface ICartService {
	
	ResponseDTO addBookToCart(CartDTO cartDTO);

}
