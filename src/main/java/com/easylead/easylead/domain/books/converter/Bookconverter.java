package com.easylead.easylead.domain.books.converter;

import com.easylead.easylead.common.annotation.Converter;
import com.easylead.easylead.domain.books.dto.BookInfoResDTO;
import com.easylead.easylead.domain.books.dto.ContentResDTO;
import com.easylead.easylead.domain.books.entity.Book;
import com.easylead.easylead.domain.content.entity.Content;
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

    public ContentResDTO toContentResDTO(Content content){
        return ContentResDTO.builder()
            .imgUrl(content.getPageImg())
            .content(content.getPageContent()).build();
    }

  public BookInfoResDTO toBookInfoResDTO(Book book) {
      return BookInfoResDTO.builder()
          .author(book.getAuthor())
          .ISBN(book.getISBN())
          .cover(book.getCover())
          .title(book.getTitle())
          .publisher(book.getPublisher())
          .build();
  }
}
