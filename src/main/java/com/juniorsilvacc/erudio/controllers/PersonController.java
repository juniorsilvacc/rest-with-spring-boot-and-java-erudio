package com.juniorsilvacc.erudio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juniorsilvacc.erudio.dtos.PersonDTO;
import com.juniorsilvacc.erudio.models.Person;
import com.juniorsilvacc.erudio.services.PersonService;

@RestController
@RequestMapping(value = "/api/person/v1")
public class PersonController {

	@Autowired
	private PersonService service;

	@GetMapping(
			value = "/{id}", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public PersonDTO findById(@PathVariable Long id) {
		return service.findById(id);
	}

	@GetMapping(
			value = "/", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<PersonDTO> findAll() {
		return service.findAll();
	}

	@PostMapping(
			value = "/", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public PersonDTO create(@RequestBody Person person) {
		return service.create(person);
	}

	@PutMapping(
			value = "/{id}", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public PersonDTO update(@RequestBody Person person, @PathVariable Long id) {
		return service.update(person, id);
	}

	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
