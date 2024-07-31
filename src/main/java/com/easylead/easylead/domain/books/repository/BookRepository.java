package com.easylead.easylead.domain.books.repository;

import com.easylead.easylead.domain.books.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,String> {
}
