package com.easylead.easylead.domain.request.converter;

import com.easylead.easylead.common.annotation.Converter;
import com.easylead.easylead.domain.books.dto.BookInfoResDTO;
import com.easylead.easylead.domain.books.dto.BookSearchResDTO;
import com.easylead.easylead.domain.request.entity.Progress;
import com.easylead.easylead.domain.request.entity.Request;
import com.easylead.easylead.domain.request.entity.RequestId;
import com.easylead.easylead.domain.users.entity.Users;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class RequestConverter {

    public Request toRequest(Long userId, String isbn, Users user, BookInfoResDTO resDTO) {
        return Request.builder()
                .requestId(new RequestId(userId,isbn))
                .readUser(user)
                .progress(Progress.P0)
                .title(resDTO.getTitle())
                .cover(resDTO.getCover())
                .author(resDTO.getAuthor())
                .publisher(resDTO.getPublisher())
                .build();
    }
}
