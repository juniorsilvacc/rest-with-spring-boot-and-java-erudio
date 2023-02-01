package com.juniorsilvacc.erudio.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juniorsilvacc.erudio.dtos.BookDTO;
import com.juniorsilvacc.erudio.models.Book;
import com.juniorsilvacc.erudio.repositories.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository repository;

	public List<BookDTO> findAll() {
		List<Book> books = repository.findAll();
		
		var listBooks = books.stream().map(BookDTO::new).collect(Collectors.toList());
		
		return listBooks;
	}

}
