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

import com.juniorsilvacc.erudio.controllers.PersonController;
import com.juniorsilvacc.erudio.dtos.PersonDTO;
import com.juniorsilvacc.erudio.exceptions.RequiredObjectIsNullException;
import com.juniorsilvacc.erudio.exceptions.ResourceNotFoundException;
import com.juniorsilvacc.erudio.models.Person;
import com.juniorsilvacc.erudio.repositories.PersonRepository;

import jakarta.transaction.Transactional;

@Service
public class PersonService {

	@Autowired
	private PersonRepository repository;
	
	@Autowired
	PagedResourcesAssembler<PersonDTO> assembler;
	
	public PersonDTO findById(Long id) {
		
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		PersonDTO dto = new PersonDTO(entity);
		
		dto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		
		return dto;
		
	}
	
	public PagedModel<EntityModel<PersonDTO>> findAll(Pageable pageable) {
//		var persons = repository.findAll();
//		
//		var listPersons = persons.stream().map(PersonDTO::new).collect(Collectors.toList());
//		
//		listPersons.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getId())).withSelfRel()));
//		
//		return listPersons;
		var personPage = repository.findAll(pageable);
		
		var personDtoPage = personPage.map(PersonDTO::new);
		personDtoPage.map(
				p -> p.add(
						linkTo(methodOn(PersonController.class)
								.findById(p.getId())).withSelfRel()));
		
		Link link = linkTo(methodOn(PersonController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
		return assembler.toModel(personDtoPage, link );
	}
	
	public PagedModel<EntityModel<PersonDTO>> findPersonByName(String firstName, Pageable pageable) {
		var personPage = repository.findPersonByName(firstName, pageable);
		
		var personDtoPage = personPage.map(PersonDTO::new);
		personDtoPage.map(
				p -> p.add(
						linkTo(methodOn(PersonController.class)
								.findById(p.getId())).withSelfRel()));
		
		Link link = linkTo(methodOn(PersonController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
		return assembler.toModel(personDtoPage, link );
	}
	
	public PersonDTO create(Person person) {
		if(person == null) throw new RequiredObjectIsNullException();
		
		Person newObj = repository.save(person);
		
		PersonDTO dto = new PersonDTO(newObj);
		
		dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel());
		
		return dto;
	}
	
	public PersonDTO update(Person person, Long id) {
		if(person == null) throw new RequiredObjectIsNullException();
		
		Optional<Person> oldPerson = repository.findById(id);

		if(!oldPerson.isPresent()) {
			new ResourceNotFoundException("No records found for this ID");
		}
		
		BeanUtils.copyProperties(person, oldPerson.get(), "id");
		
		Person newPerson = repository.save(oldPerson.get());
		
		PersonDTO dto = new PersonDTO(newPerson);
		
		dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel());
		
		return dto;
	}
	
	public void delete(Long id) {
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		repository.delete(entity);
	}
	
	@Transactional
	public PersonDTO disabledPerson(Long id) {
		repository.disabledPerson(id);
		
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		PersonDTO dto = new PersonDTO(entity);
		
		dto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		
		return dto;
		
	}
	
}
