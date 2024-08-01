package com.easylead.easylead.domain.content.service;

import com.easylead.easylead.domain.content.entity.Content;
import com.easylead.easylead.domain.content.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;

    public void save(Content content) {

        contentRepository.save(content);

    }
}
