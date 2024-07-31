package com.easylead.easylead.domain.books.service;

import com.easylead.easylead.domain.books.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    public boolean isBook(String ISBN){

        if(bookRepository.findById(ISBN).isPresent())
            return true;
        else
            return false;

    }
}
