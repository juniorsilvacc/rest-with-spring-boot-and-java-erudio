package com.juniorsilvacc.erudio.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.juniorsilvacc.erudio.configs.TestConfigs;

import com.juniorsilvacc.erudio.dtos.security.TokenDTO;
import com.juniorsilvacc.erudio.integrationtests.dto.AccountCredentialsDTO;
import com.juniorsilvacc.erudio.integrationtests.dto.PersonDTO;
import com.juniorsilvacc.erudio.integrationtests.testcontainer.AbstractIntegrationTest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	
	private static PersonDTO person;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		person = new PersonDTO();
	}
	
	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsDTO user = new AccountCredentialsDTO("junior", "admin123");
		
		var accessToken =
				given()
					.basePath("/auth/signin")
						.port(TestConfigs.SERVER_PORT)
						.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.body(user)
						.when()	
					.post()
						.then()
							.statusCode(200)
								.extract()
								.body()
									.as(TokenDTO.class)
								.getAccessToken();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/person/v1/")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockPerson();
				
		var content =
			given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
					.body(person)
					.when()	
					.post()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();
		
		PersonDTO createPerson = objectMapper.readValue(content, PersonDTO.class);
		person = createPerson;
		
		assertNotNull(createPerson);
		
		assertNotNull(createPerson.getId());
		assertNotNull(createPerson.getFirstName());
		assertNotNull(createPerson.getLastName());
		assertNotNull(createPerson.getAddress());
		assertNotNull(createPerson.getGender());
		
		assertTrue(createPerson.getId() > 0);
		
		assertEquals("Paul", createPerson.getFirstName());
		assertEquals("Walker", createPerson.getLastName());
		assertEquals("New York City, New York - US", createPerson.getAddress());
		assertEquals("Male", createPerson.getGender());
	}
	
	@Test
	@Order(2)
	public void testCreateWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		var content =
				given().spec(specification)
					.contentType(TestConfigs.CONTENT_TYPE_JSON)
						.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SEMERU)
						.body(person)
						.when()	
						.post()
					.then()
						.statusCode(403)
							.extract()
							.body()
								.asString();
		
		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
	}
	
	@Test
	@Order(3)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
					.pathParam("id", person.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();
		
		PersonDTO createPerson = objectMapper.readValue(content, PersonDTO.class);
		person = createPerson;
		
		assertNotNull(createPerson);
		
		assertNotNull(createPerson.getId());
		assertNotNull(createPerson.getFirstName());
		assertNotNull(createPerson.getLastName());
		assertNotNull(createPerson.getAddress());
		assertNotNull(createPerson.getGender());
		
		assertTrue(createPerson.getId() > 0);
		
		assertEquals("Paul", createPerson.getFirstName());
		assertEquals("Walker", createPerson.getLastName());
		assertEquals("New York City, New York - US", createPerson.getAddress());
		assertEquals("Male", createPerson.getGender());
	}
	
	@Test
	@Order(4)
	public void testFindByIdWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SEMERU)
					.pathParam("id", person.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(403)
						.extract()
						.body()
							.asString();

		
		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
	}

	private void mockPerson() {
		person.setId(1L);
		person.setFirstName("Paul");
		person.setLastName("Walker");
		person.setAddress("New York City, New York - US");
		person.setGender("Male");
	}
	
}
