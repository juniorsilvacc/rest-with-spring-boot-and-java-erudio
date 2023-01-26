package com.juniorsilvacc.erudio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juniorsilvacc.erudio.exceptions.ResourceNotFoundException;
import com.juniorsilvacc.erudio.models.Person;
import com.juniorsilvacc.erudio.repositories.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository repository;
	
	public Person findById(Long id) {
		
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
	}
	
	public List<Person> findAll() {
		return repository.findAll();
	}
	
	public Person create(Person person) {

		return repository.save(person);
		
	}
	
	public Person update(Person person, Long id) {
		Optional<Person> oldPerson = repository.findById(id);

		if(!oldPerson.isPresent()) {
			new ResourceNotFoundException("No records found for this ID");
		}
		
		BeanUtils.copyProperties(person, oldPerson.get(), "id");
		
		return repository.save(oldPerson.get());
		
	}
	
	public void delete(Long id) {
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		repository.delete(entity);
	}
	
}
