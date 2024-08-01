package com.easylead.easylead.domain.books.repository;

import com.easylead.easylead.domain.books.entity.Book;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book,String> {

  @Query("SELECT b FROM Book b ORDER BY b.createdAt DESC")
  public List<Book> recentBookList(Pageable pageable);

  @Query("SELECT b FROM Book b ORDER BY b.view DESC")
  public List<Book> highViewList(Pageable pageable);

  @Query("SELECT b FROM Book b WHERE b.ISBN IN ('8954688993','K342737878','K942939739','8954427154','K122930138')")
  List<Book> recommentList(Pageable pageable);
}
