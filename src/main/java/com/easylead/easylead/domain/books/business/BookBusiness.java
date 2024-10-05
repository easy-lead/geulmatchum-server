package com.easylead.easylead.domain.books.business;

import com.easylead.easylead.common.annotation.Business;
import com.easylead.easylead.domain.books.converter.Bookconverter;
import com.easylead.easylead.domain.books.dto.BookContentResDTO;
import com.easylead.easylead.domain.books.dto.BookInfoResDTO;
import com.easylead.easylead.domain.books.dto.BookSearchResDTO;
import com.easylead.easylead.domain.books.dto.ContentResDTO;
import com.easylead.easylead.domain.books.entity.Book;
import com.easylead.easylead.domain.books.entity.Origin;
import com.easylead.easylead.domain.books.service.AladinService;
import com.easylead.easylead.domain.books.service.BookService;
import com.easylead.easylead.domain.content.converter.ContentConverter;
import com.easylead.easylead.domain.content.entity.Content;
import com.easylead.easylead.domain.content.service.ContentService;
import com.easylead.easylead.domain.gpt.service.GptService;
import com.easylead.easylead.domain.read.entity.Read;
import com.easylead.easylead.domain.read.entity.ReadId;
import com.easylead.easylead.domain.read.service.ReadService;
import com.easylead.easylead.domain.request.entity.Progress;
import com.easylead.easylead.domain.request.entity.Request;
import com.easylead.easylead.domain.request.service.RequestService;
import com.easylead.easylead.domain.users.entity.Users;
import com.easylead.easylead.domain.users.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

@Business
@RequiredArgsConstructor
public class BookBusiness {

    private final BookService bookService;
    private final AladinService aladinService;
    private final RequestService requestService;
    private final GptService gptService;
    private final ContentConverter contentConverter;
    private final ContentService contentService;
    private final Bookconverter bookConverter;
    private final UserService userService;
    private final ReadService readService;

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

    @Transactional
    public void easyToRead(String isbn) throws JsonProcessingException {
        List<Origin> originList = bookService.findOriginByISBN(isbn);

        Request request = requestService.findByISBN(isbn);

        Book book = bookConverter.toBook(request);
        System.out.println(book.getISBN()+"  "+ book.getTitle());
        Book saveBook = bookService.save(book);
//        for(Origin originContent : originList){
//
//            String easyContent= null;
//            String imgUrl = null;
//            try {
//                // 두 비동기 작업을 병렬로 시작
//                CompletableFuture<String> easyContentFuture = bookService.transformContent(originContent.getPageContent());
//                CompletableFuture<String> imgUrlFuture = bookService.makeImage(originContent.getPageContent());
//
//                // 두 작업이 모두 완료될 때까지 기다림
//                easyContent = easyContentFuture.get();
//                imgUrl = imgUrlFuture.get();
//            } catch (InterruptedException | ExecutionException e) {
//                throw new RuntimeException(e);
//            }
//
//          Content content = contentConverter.toContent(saveBook,easyContent,originContent.getOriginId(),imgUrl);
//            contentService.save(content);
//        }
        request.updateProgress(Progress.P4);
        requestService.update(request);

    }
    @Transactional
    public BookContentResDTO readContent(Long userId, Long pageId, String isbn) {

        Long maxPage = contentService.findMaxPage(isbn);
        List<Content> pageContent = contentService.getPageContent(pageId,isbn);
        List<ContentResDTO> contentList = pageContent.stream().map(bookConverter::toContentResDTO).toList();
        Users user = userService.findById(userId);
        Read read = readService.findById(userId, isbn);
        Book book = bookService.findByISBN(isbn);
        if(read ==null)
            read = Read.builder().readId(new ReadId(userId,isbn)).readUser(user).book(book).build();
        read.updatePage(pageId);
        readService.save(read);


        return BookContentResDTO.builder().pageId(pageId).content(contentList).maxPage(maxPage).build();
    }

  public List<BookInfoResDTO> recentList() {
        List<Book> bookList = bookService.getRecentList();
        return bookList.stream().map(bookConverter::toBookInfoResDTO).toList();
  }

    public List<BookInfoResDTO> highViewList() {
        List<Book> bookList = bookService.getHighViewList();
        return bookList.stream().map(bookConverter::toBookInfoResDTO).toList();
    }

  public List<BookInfoResDTO> recommendList() {
      List<Book> bookList = bookService.getRecommendList();
      return bookList.stream().map(bookConverter::toBookInfoResDTO).toList();
  }
}
