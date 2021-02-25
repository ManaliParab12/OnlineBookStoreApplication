package com.bridgelabz.onlinebookstore.service;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.exception.UserException;
import com.bridgelabz.onlinebookstore.model.Book;
import com.bridgelabz.onlinebookstore.model.User;
import com.bridgelabz.onlinebookstore.repository.BookRepository;
import com.bridgelabz.onlinebookstore.repository.UserRepository;
import com.bridgelabz.onlinebookstore.utility.Token;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.modelmapper.ModelMapper;


@Service
@PropertySource("classpath:status.properties")
public class BookService implements IBookService {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private UserRepository userRepository;

	
	@Override
	public ResponseDTO addBook(String token, BookDTO bookDTO) throws UserException {
		int id = Token.decodeToken(token);
		User user = userRepository.findById(id)
				.orElseThrow(() ->  new UserException(environment.getProperty("status.login.error.message")));
		Book book =new Book(bookDTO);
		if(user.getType().equalsIgnoreCase("admin")) {
			bookRepository.save(book);
			return new ResponseDTO("Book Added Successfully");
		} else {
			return new ResponseDTO("You do not have permission to add book");	
		}	
	}
	
	
	@Override
	public ResponseDTO addAllBook(String token) throws UserException {
		int id = Token.decodeToken(token);
		System.out.println("Printing token" +id);
		User user = userRepository.findById(id)
					.orElseThrow(() ->  new UserException(environment.getProperty("status.login.error.message")));
		if(user.getType().equalsIgnoreCase("admin")) {
		List<Book> bookList = getBookFromCsv();
		bookList.forEach(book -> {
			book.setBookQuantity(5);
			bookRepository.save(book);
		});
		return new ResponseDTO("Book Added Successfully");	
	} else {
		return new ResponseDTO("You do not have permission to add book");	
	}			
}
	
	
	@Override
	public List<Book> getAllBooks() {
		List<Book> books = bookRepository.findAll();
		return books;
	}
	
	private List<Book> getBookFromCsv() {
		try (Reader reader = Files.newBufferedReader(Paths.get("./src/main/resources/books_data.csv"));){
				CsvToBeanBuilder<Book> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
	            csvToBeanBuilder.withType(Book.class);
	            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
	            CsvToBean<Book> csvToBean = csvToBeanBuilder.build();
	            Iterator<Book> bookCSVIterator = csvToBean.iterator();
	            List <Book> bookList = new ArrayList<>();
				 while (bookCSVIterator.hasNext()) {
		                Book bookData = bookCSVIterator.next();
		                bookList.add(bookData);
		            }
		            System.out.println(bookList);
		            return bookList;
		}catch(Exception e) {
		System.out.println("Exception while reading csv" + e);
	}
	return null;
	}	
}
