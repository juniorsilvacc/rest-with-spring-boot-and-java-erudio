package com.juniorsilvacc.erudio.dtos;

import java.io.Serializable;
import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.juniorsilvacc.erudio.models.Book;

@JsonPropertyOrder({"id", "author", "launch_date", "price", "title"})
public class BookDTO extends RepresentationModel<BookDTO> implements Serializable {

	private static final long serialVersionUID = 1L; 
	
	private Long id;
	
	private String author;
	
	@JsonProperty("launch_date")
	private Date launchDate;
	
	private Double price;
	
	private String title;
	
	public BookDTO() {
	}
	
	public BookDTO(Long id, String author, Date launchDate, Double price, String title) {
		this.id = id;
		this.author = author;
		this.launchDate = launchDate;
		this.price = price;
		this.title = title;
	}
	
	public BookDTO(Book book) {
		id = book.getId();
		author = book.getAuthor();
		launchDate = book.getLaunchDate();
		price = book.getPrice();
		title = book.getTitle();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getLaunchDate() {
		return launchDate;
	}

	public void setLaunchDate(Date launchDate) {
		this.launchDate = launchDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
		
}
