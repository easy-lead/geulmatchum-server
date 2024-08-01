package com.easylead.easylead.domain.read.converter;

import com.easylead.easylead.common.annotation.Converter;
import com.easylead.easylead.domain.books.entity.Book;
import com.easylead.easylead.domain.read.entity.Read;
import com.easylead.easylead.domain.users.dto.UserReadResDTO;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class ReadConverter {

  public UserReadResDTO toUserReadResDTO(Book book, Long page, LocalDateTime updateTime) {
    return UserReadResDTO.builder()
        .ISBN(book.getISBN())
        .title(book.getTitle())
        .publisher(book.getPublisher())
        .cover(book.getCover())
        .author(book.getAuthor())
        .pageId(page)
        .updateTime(updateTime)
        .build();
  }
}
