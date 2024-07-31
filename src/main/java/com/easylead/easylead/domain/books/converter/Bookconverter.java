package com.easylead.easylead.domain.books.converter;

import com.easylead.easylead.common.annotation.Converter;
import com.easylead.easylead.domain.books.entity.Book;
import com.easylead.easylead.domain.request.entity.Request;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class Bookconverter {


    public Book toBook(Request request) {
        return Book.builder()
                .author(request.getAuthor())
                .ISBN(request.getRequestId().getISBN())
                .title(request.getTitle())
                .cover(request.getCover())
                .publisher(request.getPublisher())
                .build();
    }
}
