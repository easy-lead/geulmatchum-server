package com.easylead.easylead.domain.books.business;

import com.easylead.easylead.common.annotation.Business;
import com.easylead.easylead.domain.books.dto.BookInfoResDTO;
import com.easylead.easylead.domain.books.dto.BookSearchResDTO;
import com.easylead.easylead.domain.books.service.AladinService;
import com.easylead.easylead.domain.books.service.BookService;
import com.easylead.easylead.domain.request.service.RequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Business
@RequiredArgsConstructor
public class BookBusiness {
    private final BookService bookService;
    private final AladinService aladinService;
    private final RequestService requestService;

    public List<BookSearchResDTO> searchBook(String title, String author, String publisher) throws JsonProcessingException, UnsupportedEncodingException {

        JsonNode itemNode = aladinService.search(title,author,publisher);

        ObjectMapper objectMapper = new ObjectMapper();

        List<BookSearchResDTO> resultList = new ArrayList<>();
        for (JsonNode node : itemNode) {
            BookSearchResDTO book = objectMapper.treeToValue(node, BookSearchResDTO.class);
            if (bookService.isBook(book.getISBN())){
                book.setStatus("Y");
            }
            else if(requestService.isRequest(book.getISBN())){
                book.setStatus("R");
            }
            else
                book.setStatus("N");
            resultList.add(book);
        }
        return  resultList;

    }

    public BookInfoResDTO bookInfo(String isbn) throws JsonProcessingException {
        BookInfoResDTO info = aladinService.search(isbn);
        if (bookService.isBook(info.getISBN())){
            info.setStatus("Y");
        }
        else if(requestService.isRequest(info.getISBN())){
            info.setStatus("R");
        }
        else
            info.setStatus("N");
        return info;
    }
}
