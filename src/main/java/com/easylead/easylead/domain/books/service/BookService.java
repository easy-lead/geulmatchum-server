package com.easylead.easylead.domain.books.service;

import com.easylead.easylead.common.error.ErrorCode;
import com.easylead.easylead.common.exception.ApiException;
import com.easylead.easylead.domain.books.entity.Book;
import com.easylead.easylead.domain.books.entity.Origin;
import com.easylead.easylead.domain.books.repository.BookRepository;
import com.easylead.easylead.domain.books.repository.OriginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final OriginRepository originRepository;
    public boolean isBook(String ISBN){

        if(bookRepository.findById(ISBN).isPresent())
            return true;
        else
            return false;

    }

    public List<Origin> findOriginByISBN(String isbn) {
        return originRepository.findByISBN(isbn).orElseThrow(() -> new ApiException(ErrorCode.ISBN_NOT_FOUND));
    }

    public Book save(Book book){
        return bookRepository.save(book);
    }
}
