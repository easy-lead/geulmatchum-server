package com.easylead.easylead.domain.books.service;

import com.easylead.easylead.common.error.ErrorCode;
import com.easylead.easylead.common.exception.ApiException;
import com.easylead.easylead.domain.books.entity.Book;
import com.easylead.easylead.domain.books.entity.Origin;
import com.easylead.easylead.domain.books.repository.BookRepository;
import com.easylead.easylead.domain.books.repository.OriginRepository;
import com.easylead.easylead.domain.gpt.service.GptService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.net.http.HttpRequest;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final OriginRepository originRepository;
    private final GptService gptService;
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

    @Async("taskExecutor1")
    public CompletableFuture<String> transformContent(String pageContent) throws JsonProcessingException {
        String easyContent = null;
        do{
            HttpRequest requestGPT = gptService.requestGPT(pageContent);
            easyContent = gptService.responseGPT(requestGPT);
        }while (easyContent.equals("ERROR"));

        return CompletableFuture.completedFuture(easyContent);
    }
    @Async("taskExecutor2")
    public CompletableFuture<String>  makeImage(String pageContent) throws JsonProcessingException {
        String imgPrompt = null;
        do{
            HttpRequest requestImgPrompt = gptService.requestImgPrompt(pageContent);
            imgPrompt = gptService.responseGPT(requestImgPrompt);
        }while (imgPrompt.equals("ERROR"));


        HttpRequest requestImg = gptService.requestGPTImage(imgPrompt);
        String imgUrl = gptService.responseDalle(requestImg);
      return CompletableFuture.completedFuture(imgUrl);
    }

}
