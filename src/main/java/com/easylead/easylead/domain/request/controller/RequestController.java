package com.easylead.easylead.domain.request.controller;

import com.easylead.easylead.domain.request.business.RequestBusiness;
import com.easylead.easylead.domain.request.dto.RequestReqDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/request")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RequestController {

    private final RequestBusiness requestBusiness;
    @PostMapping("")
    public ResponseEntity<String> insertRequest(@RequestBody RequestReqDTO req) throws JsonProcessingException {
        requestBusiness.insertRequest(req.getUserId(),req.getISBN());
        return ResponseEntity.ok("success");
    }
}
