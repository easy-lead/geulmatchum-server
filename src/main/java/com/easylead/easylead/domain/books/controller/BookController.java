package com.easylead.easylead.domain.books.controller;

import com.easylead.easylead.domain.books.business.BookBusiness;
import com.easylead.easylead.domain.books.dto.BookContentResDTO;
import com.easylead.easylead.domain.books.dto.BookInfoResDTO;
import com.easylead.easylead.domain.books.dto.BookReqDTO;
import com.easylead.easylead.domain.books.dto.BookSearchResDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookController {

    private final BookBusiness bookBusiness;

    @GetMapping("/search")
    public ResponseEntity<List<BookSearchResDTO>> searchBook(@RequestParam(value = "title", required = false) String title,
                                                           @RequestParam(value = "author", required = false) String author,
                                                           @RequestParam(value = "publisher", required = false) String publisher) throws JsonProcessingException, UnsupportedEncodingException {

        return ResponseEntity.ok(bookBusiness.searchBook(title,author,publisher));
    }

    @GetMapping("/info")
    public ResponseEntity<BookInfoResDTO> bookInfo(@RequestParam(value = "isbn") String isbn) throws JsonProcessingException, UnsupportedEncodingException {

        return ResponseEntity.ok(bookBusiness.bookInfo(isbn));
    }

    @PostMapping("/publish")
    public ResponseEntity<String> easyToRead(@RequestBody BookReqDTO bookReqDTO) throws JsonProcessingException {
        bookBusiness.easyToRead(bookReqDTO.getISBN());
        return ResponseEntity.ok("success");
    }

    @GetMapping("/content")
    public ResponseEntity<BookContentResDTO> bookContent(@RequestParam Long userId, Long pageId, String ISBN){
        return ResponseEntity.ok(bookBusiness.readContent(userId,pageId,ISBN));
    }
    @GetMapping("/recent")
    public ResponseEntity<List<BookInfoResDTO>> recentList(){
        return ResponseEntity.ok(bookBusiness.recentList());
    }
    @GetMapping("/view")
    public ResponseEntity<List<BookInfoResDTO>> highViewList(){
        return ResponseEntity.ok(bookBusiness.highViewList());
    }
    @GetMapping("/recommend")
    public ResponseEntity<List<BookInfoResDTO>> recommendList(){
        return ResponseEntity.ok(bookBusiness.recommendList());
    }
}
