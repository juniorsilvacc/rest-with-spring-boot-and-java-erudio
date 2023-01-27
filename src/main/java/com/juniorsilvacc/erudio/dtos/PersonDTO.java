package com.juniorsilvacc.erudio.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.juniorsilvacc.erudio.models.Person;

@JsonPropertyOrder({"id", "firstName", "lastName", "address", "gender"})
public class PersonDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@JsonProperty("first_name")
	private String firstName;
	
	@JsonProperty("last_name")
	private String lastName;
	
	private String address;
	
	private String gender;
	
	public PersonDTO() {
	}

	public PersonDTO(Long id, String firstName, String lastName, String address, String gender) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.gender = gender;
	}
	
	public PersonDTO(Person person) {
		id = person.getId();
		firstName = person.getFirstName();
		lastName = person.getLastName();
		address = person.getAddress();
		gender = person.getGender();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
}
