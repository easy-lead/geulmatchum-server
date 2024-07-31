package com.easylead.easylead.domain.request.business;

import com.easylead.easylead.common.annotation.Business;
import com.easylead.easylead.domain.books.dto.BookInfoResDTO;
import com.easylead.easylead.domain.books.dto.BookSearchResDTO;
import com.easylead.easylead.domain.books.service.AladinService;
import com.easylead.easylead.domain.request.converter.RequestConverter;
import com.easylead.easylead.domain.request.entity.Request;
import com.easylead.easylead.domain.request.entity.RequestId;
import com.easylead.easylead.domain.request.service.RequestService;
import com.easylead.easylead.domain.users.entity.Users;
import com.easylead.easylead.domain.users.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class RequestBusiness {
    private final RequestService requestService;
    private final RequestConverter requestConverter;
    private final AladinService aladinService;
    private final UserService userService;
    public void insertRequest(Long userId, String isbn) throws JsonProcessingException {
        BookInfoResDTO result = aladinService.search(isbn);
        Users readUser = userService.findById(userId);
        Request request = requestConverter.toRequest(userId, isbn, readUser, result);
        requestService.insertRequest(request);
    }
}
