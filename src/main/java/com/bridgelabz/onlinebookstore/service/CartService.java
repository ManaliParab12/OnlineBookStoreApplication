package com.bridgelabz.onlinebookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.dto.CartDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.model.Cart;
import com.bridgelabz.onlinebookstore.repository.CartRepository;


@Service
public class CartService implements ICartService {
	
	@Autowired
	private CartRepository cartRepository;	
	
	@Override
	public ResponseDTO addBookToCart(CartDTO cartDTO) {
		Cart cart = new Cart(cartDTO);
		cartRepository.save(cart);
		return new ResponseDTO("Book Added to cart");
	}

}
