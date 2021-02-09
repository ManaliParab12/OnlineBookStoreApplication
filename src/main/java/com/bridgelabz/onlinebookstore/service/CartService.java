package com.bridgelabz.onlinebookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.dto.CartDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.exception.CartException;
import com.bridgelabz.onlinebookstore.exception.UserException;
import com.bridgelabz.onlinebookstore.model.Cart;
import com.bridgelabz.onlinebookstore.repository.BookRepository;
import com.bridgelabz.onlinebookstore.repository.CartRepository;
import com.bridgelabz.onlinebookstore.repository.UserRepository;
import com.bridgelabz.onlinebookstore.utility.Token;


@Service
@PropertySource("classpath:status.properties")
public class CartService implements ICartService {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public ResponseDTO addBookToCart(CartDTO cartDTO) throws CartException {
		Cart cart = new Cart(cartDTO);
		if(cartRepository.findByBookAndUser(cartDTO.getBook(), cartDTO.getUser()).isPresent())
			throw new CartException("Book Already Present in cart");
		cartRepository.save(cart);
		return new ResponseDTO("Book Added to cart");
	}
	
	
	@Override
	public ResponseDTO updateCart(String token, int bookId, int quantity) throws CartException, UserException {
		int userId = Token.decodeToken(token);
		Optional<Cart> carts = cartRepository.findByBookAndUser(bookRepository.findById(bookId).get()
	                						 , userRepository.findById(userId)
	                						 .orElseThrow(() -> new UserException(environment.getProperty("status.login.error.message"))));
	    Cart cart = carts.orElseThrow(() -> new CartException("INVALID_BOOK_ID"));
	    cart.setQuantity(quantity);
	    cartRepository.save(cart);
	    return new  ResponseDTO("Book Quantity updated"); 
	}
	
	
	@Override
	public List<Cart> getListOfBooksInCart(String email) throws UserException {
	    return cartRepository.findAllBooksByUser(userRepository.findByEmail(email)
	    		             .orElseThrow(() -> new UserException(environment.getProperty("status.login.error.message"))));
	}
	
	
	@Override
	public ResponseDTO removeBookFromCart(int bookId, String token) throws CartException {
		int userId = Token.decodeToken(token);
		Optional<Cart> carts = cartRepository.findByBookAndUser(bookRepository.findById(bookId).get()
		                , userRepository.findById(userId).get());
		Cart cart = carts.orElseThrow(() -> new CartException("INVALID_BOOK_ID"));
		cartRepository.deleteById(cart.getId());
		return new ResponseDTO("Book Removed from cart"); 	
	}
	
}
