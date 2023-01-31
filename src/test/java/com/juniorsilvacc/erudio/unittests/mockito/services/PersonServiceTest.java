package com.juniorsilvacc.erudio.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.juniorsilvacc.erudio.dtos.PersonDTO;
import com.juniorsilvacc.erudio.exceptions.RequiredObjectIsNullException;
import com.juniorsilvacc.erudio.models.Person;
import com.juniorsilvacc.erudio.repositories.PersonRepository;
import com.juniorsilvacc.erudio.services.PersonService;
import com.juniorsilvacc.erudio.unittests.mocks.MockPerson;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
	
	MockPerson input;
	
	@InjectMocks
	private PersonService service;
	
	@Mock
	PersonRepository repository;

	@BeforeEach
	void setMocks() throws Exception {
		input = new MockPerson();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		Person entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.findById(1L);
		
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		
		System.out.println(result.toString());
		assertNotNull(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Addres Test1", result.getAddress());
		assertEquals("Female", result.getGender());
	}

	@Test
	void testFindAll() {
		fail("Not yet implemented");
	}

	@Test
	void testCreate() {
		Person entity = input.mockEntity(1);
		
		Person persisted = entity;
		persisted.setId(1L);
		
		PersonDTO dto = input.mockDTO(1);
		dto.setId(1L);
		
	
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.create(persisted);
		
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		
		assertNotNull(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Addres Test1", result.getAddress());
		assertEquals("Female", result.getGender());
	}
	
	@Test
	void testCreateWithNullPerson() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});
		
		String expectedMessage = "It is not allowed to persist a null object";
		String actualMessage = exception.getMessage();
		
		assertNotNull(actualMessage.contains(expectedMessage));
	}

	@Test
	void testUpdate() {
		Person entity = input.mockEntity(1);
		
		Person persisted = entity;
		persisted.setId(1L);
		
		PersonDTO dto = input.mockDTO(1);
		dto.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.update(persisted, persisted.getId());
		
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		
		assertNotNull(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Addres Test1", result.getAddress());
		assertEquals("Female", result.getGender());
	}
	
	@Test
	void testUpdateWithNullPerson() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.update(null, null);
		});
		
		String expectedMessage = "It is not allowed to persist a null object";
		String actualMessage = exception.getMessage();
		
		assertNotNull(actualMessage.contains(expectedMessage));
	}

	@Test
	void testDelete() {
		Person entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		service.delete(1L);
	}

}
