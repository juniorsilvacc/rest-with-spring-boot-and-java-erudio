package com.juniorsilvacc.erudio.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
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

import com.juniorsilvacc.erudio.dtos.BookDTO;
import com.juniorsilvacc.erudio.exceptions.RequiredObjectIsNullException;
import com.juniorsilvacc.erudio.models.Book;
import com.juniorsilvacc.erudio.repositories.BookRepository;
import com.juniorsilvacc.erudio.services.BookService;
import com.juniorsilvacc.erudio.unittests.mocks.MockBook;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {
	
	MockBook input;
	
	@InjectMocks
	private BookService service;
	
	@Mock
	BookRepository repository;

	@BeforeEach
	void setMocks() throws Exception {
		input = new MockBook();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		Book entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.findById(1L);
		
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		
		assertNotNull(result.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
		
		assertEquals("Some Author1", result.getAuthor());
		assertEquals("Some Title1", result.getTitle());
		assertEquals(25D, result.getPrice());
		assertNotNull(result.getLaunchDate());
	}
	
	@Test
	void testCreate() {
		Book entity = input.mockEntity(1);
		
		Book persisted = entity;
		persisted.setId(1L);
		
		BookDTO dto = input.mockDTO(1);
		dto.setId(1L);
		
	
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.create(persisted);
		
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		
		assertNotNull(result.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
		
		assertEquals("Some Author1", result.getAuthor());
		assertEquals("Some Title1", result.getTitle());
		assertEquals(25D, result.getPrice());
		assertNotNull(result.getLaunchDate());
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
		Book entity = input.mockEntity(1);
		
		Book persisted = entity;
		persisted.setId(1L);
		
		BookDTO dto = input.mockDTO(1);
		dto.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.update(persisted, persisted.getId());
		
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		
		assertNotNull(result.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
		
		assertEquals("Some Author1", result.getAuthor());
		assertEquals("Some Title1", result.getTitle());
		assertEquals(25D, result.getPrice());
		assertNotNull(result.getLaunchDate());
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
		Book entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		service.delete(1L);
	}
	
	@Test
	void testFindAll() {
		List<Book> list = input.mockEntityList();
		
		when(repository.findAll()).thenReturn(list);
		
		var book = service.findAll();
		
		assertNotNull(book);
		assertEquals(14, book.size());
		
		var bookOne = book.get(1);
		
		assertNotNull(bookOne);
		assertNotNull(bookOne.getId());
		assertNotNull(bookOne.getLinks());
		
		assertNotNull(bookOne.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
		
		assertEquals("Some Author1", bookOne.getAuthor());
		assertEquals("Some Title1", bookOne.getTitle());
		assertEquals(25D, bookOne.getPrice());
		assertNotNull(bookOne.getLaunchDate());
		
		var bookTwo = book.get(2);
		
		assertNotNull(bookTwo);
		assertNotNull(bookTwo.getId());
		assertNotNull(bookTwo.getLinks());
		
		assertNotNull(bookTwo.toString().contains("links: [</api/books/v1/2>;rel=\"self\"]"));
		
		assertEquals("Some Author2", bookTwo.getAuthor());
		assertEquals("Some Title2", bookTwo.getTitle());
		assertEquals(25D, bookTwo.getPrice());
		assertNotNull(bookTwo.getLaunchDate());
	
		var bookTree = book.get(3);
		
		assertNotNull(bookTree);
		assertNotNull(bookTree.getId());
		assertNotNull(bookTree.getLinks());
		
		assertNotNull(bookTwo.toString().contains("links: [</api/books/v1/3>;rel=\"self\"]"));
		
		assertEquals("Some Author3", bookTree.getAuthor());
		assertEquals("Some Title3", bookTree.getTitle());
		assertEquals(25D, bookTree.getPrice());
		assertNotNull(bookTree.getLaunchDate());
	}
}
