package com.juniorsilvacc.erudio.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.juniorsilvacc.erudio.models.Person;

@Service
public class PersonService {

	private final AtomicLong counter = new AtomicLong();
	private Logger logger = Logger.getLogger(PersonService.class.getName());
	
	public Person findById(String id) {
		
		logger.info("Finding one person");
		
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("Junior");
		person.setLastName("Silva");
		person.setAddress("Paraíba - Brasil");
		person.setGender("Male");
		
		return person;
		
	}
	
	public List<Person> findAll() {
		List<Person> persons = new ArrayList<>();
		
		for(int x = 0; x < 8; x++) {
			Person person = mockPerson(x);
			persons.add(person);
		}
		
		return persons;
	}
	
	public Person create(Person person) {

		logger.info("Creating person");
		
		return person;
	}
	
	public Person update(Person person, String id) {

		logger.info("Updating person");
		
		return person;
	}
	
	public void delete(String id) {
		
		logger.info("Delete person");
		
	}

	private Person mockPerson(int x) {
		Person person = new Person();
		
		person.setId(counter.incrementAndGet());
		person.setFirstName("Person name " + x);
		person.setLastName("Last name " + x);
		person.setAddress("Some address in Brasil " + x);
		person.setGender("Male " + x);
		
		return person;
	}
	
}
