package com.bridgelabz.onlinebookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.onlinebookstore.dto.CartDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.service.ICartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private ICartService cartService;
	
	
	@PostMapping("/add")
	public ResponseEntity<ResponseDTO> addBookToCart(@RequestBody CartDTO cartDTO) { 
		ResponseDTO responseDTO = cartService.addBookToCart(cartDTO);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

}
