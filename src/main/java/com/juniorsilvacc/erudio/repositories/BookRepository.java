package com.juniorsilvacc.erudio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juniorsilvacc.erudio.models.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
}
