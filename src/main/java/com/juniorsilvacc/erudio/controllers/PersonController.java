package com.juniorsilvacc.erudio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juniorsilvacc.erudio.dtos.PersonDTO;
import com.juniorsilvacc.erudio.models.Person;
import com.juniorsilvacc.erudio.services.PersonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api/person/v1")
@Tag(name = "People", description = "Endpoints for Managing People")
public class PersonController {

	@Autowired
	private PersonService service;

	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping(
			value = "/{id}", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@Operation(summary = "Finds a People", description = "Finds a people", 
	tags = {"People"}, 
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
	public PersonDTO findById(@PathVariable Long id) {
		return service.findById(id);
	}

	@GetMapping(
			value = "/", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@Operation(summary = "Finds all People", description = "Finds all people", 
	tags = {"People"}, 
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
	public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "12") Integer limite,
			@RequestParam(value = "direction", defaultValue = "asc")  String direction
			) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, limite, Sort.by(sortDirection, "firstName"));
		return ResponseEntity.ok(service.findAll(pageable));
	}
	
	@GetMapping(
			value = "/findPersonByName/{firstName}", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@Operation(summary = "Find People by name", description = "Find People by name", 
	tags = {"People"}, 
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
	public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findPersonByName(
			@PathVariable(value = "firstName")  String firstName,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "12") Integer limite,
			@RequestParam(value = "direction", defaultValue = "asc")  String direction
			) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, limite, Sort.by(sortDirection, "firstName"));
		return ResponseEntity.ok(service.findPersonByName(firstName, pageable));
	}

	@CrossOrigin(origins = {"http://localhost:8080", "https://erudio.com.br"})
	@PostMapping(
			value = "/", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@Operation(
			summary = "Add a new Person", 
			description = "Add a new person by passing in a JSON or XML representation of the person", 
	tags = {"People"}, 
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", 
					content = @Content (schema = @Schema(implementation = PersonDTO.class))
				),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unaunthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
		}
	)
	public PersonDTO create(@RequestBody Person person) {
		return service.create(person);
	}

	@PutMapping(
			value = "/{id}", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@Operation(
			summary = "Update a Person", 
			description = "Update a person by passing in a JSON or XML representation of the person", 
	tags = {"People"}, 
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
	public PersonDTO update(@RequestBody Person person, @PathVariable Long id) {
		return service.update(person, id);
	}

	@DeleteMapping(value = "/{id}")
	@Operation(
			summary = "Delete a Person", 
			description = "Delete a person by passing in a JSON or XML representation of the person", 
	tags = {"People"}, 
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
	
	@PatchMapping(
			value = "/{id}", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@Operation(summary = "Disabled people", description = "Disabled a specific People by your ID", 
	tags = {"People"}, 
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
	public PersonDTO disabledPerson(@PathVariable Long id) {
		return service.disabledPerson(id);
	}
}
