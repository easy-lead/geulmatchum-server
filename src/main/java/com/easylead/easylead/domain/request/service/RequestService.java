package com.easylead.easylead.domain.request.service;

import com.easylead.easylead.domain.request.entity.Request;
import com.easylead.easylead.domain.request.entity.RequestId;
import com.easylead.easylead.domain.request.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
