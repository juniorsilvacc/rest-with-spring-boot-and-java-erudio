package com.juniorsilvacc.erudio.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juniorsilvacc.erudio.controllers.PersonController;
import com.juniorsilvacc.erudio.dtos.BookDTO;
import com.juniorsilvacc.erudio.exceptions.ResourceNotFoundException;
import com.juniorsilvacc.erudio.models.Book;
import com.juniorsilvacc.erudio.repositories.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository repository;

	public List<BookDTO> findAll() {
		List<Book> books = repository.findAll();
		
		var listBooks = books.stream().map(BookDTO::new).collect(Collectors.toList());
		
		listBooks.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getId())).withSelfRel()));
		
		return listBooks;
	}

	public BookDTO findById(Long id) {
		Book entity = repository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		BookDTO dto = new BookDTO(entity);
		
		dto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		
		return dto;
	}
	
	

}
