package com.juniorsilvacc.erudio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juniorsilvacc.erudio.dtos.BookDTO;
import com.juniorsilvacc.erudio.dtos.PersonDTO;
import com.juniorsilvacc.erudio.models.Book;
import com.juniorsilvacc.erudio.services.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(value = "/api/books/v1")
public class BookController {
	
	@Autowired
	private BookService service;

	@GetMapping(
			value = "/", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@Operation(summary = "Finds all Book", description = "Finds all Book", 
	tags = {"Book"}, 
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", 
					content = {
							@Content (
								mediaType = "application/json",
								array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))
							)
					}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unaunthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
		}
	)
	public List<BookDTO> findAll() {
		return service.findAll();
	}
	
	@GetMapping(
			value = "/{id}", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@Operation(summary = "Finds a Book", description = "Finds a Book", 
	tags = {"Book"}, 
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", 
					content = @Content (schema = @Schema(implementation = PersonDTO.class))
				),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unaunthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
		}
	)
	public BookDTO findById(@PathVariable Long id) {
		return service.findById(id);
	}
	
	@PostMapping(value = "/")
	public BookDTO create(@RequestBody Book book) {
		return service.create(book);
	}
	
	@PutMapping(
			value = "/{id}", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@Operation(
			summary = "Update a Book", 
			description = "Update a book by passing in a JSON or XML representation of the book", 
	tags = {"Book"}, 
	responses = {
			@ApiResponse(description = "Updated", responseCode = "200", 
					content = @Content (schema = @Schema(implementation = PersonDTO.class))
				),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unaunthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
		}
	)
	public BookDTO update(@RequestBody Book book, @PathVariable Long id) {
		return service.update(book, id);
	}
	
	@DeleteMapping(value = "/{id}")
	@Operation(
			summary = "Delete a Book", 
			description = "Delete a book by passing in a JSON or XML representation of the book", 
	tags = {"Book"}, 
	responses = {
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Unaunthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
		}
	)
	public ResponseEntity<?>  delete(@PathVariable Long id) {
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}

}
