package com.juniorsilvacc.erudio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juniorsilvacc.erudio.models.Person;
import com.juniorsilvacc.erudio.services.PersonService;

@RestController
@RequestMapping(value = "/person")
public class PersonController {
	
	@Autowired
	private PersonService service;
	
	@GetMapping(value = "/{id}")
	public Person findById(@PathVariable String id) {
		return service.findById(id);
	}
	
	@GetMapping(value = "/")
	public List<Person> findAll() {
		return service.findAll();
	}
	
	@PostMapping(value = "/")
	public Person create(@RequestBody Person person) {
		return service.create(person);
	}

	@PutMapping(value = "/{id}")
	public Person update(@RequestBody Person person, @PathVariable String id) {
		return service.update(person, id);
	}
	
	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable String id) {
		service.delete(id);
	}
}