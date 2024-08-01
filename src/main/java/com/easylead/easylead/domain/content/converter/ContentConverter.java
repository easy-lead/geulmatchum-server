package com.easylead.easylead.domain.content.converter;


import com.easylead.easylead.common.annotation.Converter;
import com.easylead.easylead.domain.books.entity.Book;
import com.easylead.easylead.domain.books.entity.OriginId;
import com.easylead.easylead.domain.content.entity.Content;
import com.easylead.easylead.domain.content.entity.ContentId;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class ContentConverter {


    public Content toContent(Book book, String easyContent, OriginId originId, String imgUrl) {
        return Content.builder()
                .pageContent(easyContent)
                .contentId(new ContentId(originId.getPageId(), originId.getISBN()))
                .book(book)
                .pageImg(imgUrl)
                .build();
    }
}
