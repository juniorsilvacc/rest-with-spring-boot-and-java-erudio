package com.juniorsilvacc.erudio.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import com.juniorsilvacc.erudio.controllers.BookController;
import com.juniorsilvacc.erudio.dtos.BookDTO;
import com.juniorsilvacc.erudio.exceptions.RequiredObjectIsNullException;
import com.juniorsilvacc.erudio.exceptions.ResourceNotFoundException;
import com.juniorsilvacc.erudio.models.Book;
import com.juniorsilvacc.erudio.repositories.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository repository;
	
	@Autowired
	PagedResourcesAssembler<BookDTO> assembler;

	public PagedModel<EntityModel<BookDTO>> findAll(Pageable pageable) {
//		List<Book> books = repository.findAll();
//		
//		var listBooks = books.stream().map(BookDTO::new).collect(Collectors.toList());
//		
//		listBooks.stream().forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getId())).withSelfRel()));
//		
//		return listBooks;
		var bookPage = repository.findAll(pageable);
		
		var bookDtoPage = bookPage.map(BookDTO::new);
		bookDtoPage.map(
				p -> p.add(
						linkTo(methodOn(BookController.class)
								.findById(p.getId())).withSelfRel()));
		
		Link link = linkTo(methodOn(BookController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
		return assembler.toModel(bookDtoPage, link );
		
	}

	public BookDTO findById(Long id) {
		Book entity = repository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		BookDTO dto = new BookDTO(entity);
		
		dto.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		
		return dto;
	}

	public BookDTO create(Book book) {
		if(book == null) throw new RequiredObjectIsNullException();
		
		Book newBook = repository.save(book);
		
		BookDTO dto = new BookDTO(newBook);
		
		dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel());
		
		return dto;
	}

	public BookDTO update(Book book, Long id) {
		if(book == null) throw new RequiredObjectIsNullException();
		
		Optional<Book> oldBook = repository.findById(id);

		if(!oldBook.isPresent()) {
			new ResourceNotFoundException("No records found for this ID");
		}
		
		BeanUtils.copyProperties(book, oldBook.get(), "id");
		
		Book newBook = repository.save(oldBook.get());
		
		BookDTO dto = new BookDTO(newBook);
		
		dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel());
		
		return dto;
	}

	public void delete(Long id) {
		Book entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		repository.delete(entity);
	}
	
	

}
