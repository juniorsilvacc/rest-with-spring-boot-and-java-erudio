package com.juniorsilvacc.erudio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juniorsilvacc.erudio.dtos.BookDTO;
import com.juniorsilvacc.erudio.services.BookService;

@RestController
@RequestMapping(value = "/api/book/v1")
public class BookController {
	
	@Autowired
	private BookService service;

	@GetMapping(value = "/")
	public List<BookDTO> findAll() {
		return service.findAll();
	}
	
	@GetMapping(value = "/{id}")
	public BookDTO findById(@PathVariable Long id) {
		return service.findById(id);
	}

}