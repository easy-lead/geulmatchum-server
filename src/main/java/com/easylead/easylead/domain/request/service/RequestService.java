package com.easylead.easylead.domain.request.service;

import com.easylead.easylead.domain.request.entity.Progress;
import com.easylead.easylead.domain.request.entity.Request;
import com.easylead.easylead.domain.request.entity.RequestId;
import com.easylead.easylead.domain.request.repository.RequestRepository;
import com.easylead.easylead.domain.users.entity.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    public boolean isRequest(String ISBN){

        if(!requestRepository.findByRequestIdISBN(ISBN).isEmpty())
            return true;
        else
            return false;

    }

    public void insertRequest(Request request) {
        requestRepository.save(request);
    }

    public Request findByISBN(String isbn) {
        List<Request> requestList = requestRepository.findByRequestIdISBN(isbn);
        System.out.println(requestList.size());
        return requestList.get(0);
    }

  public void update(Request request) {
        requestRepository.save(request);
  }

  public List<Request> findByUser(Users user) {
      return requestRepository.findByUser(user);
  }
}
