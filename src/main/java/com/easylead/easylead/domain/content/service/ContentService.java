package com.easylead.easylead.domain.content.service;

import com.easylead.easylead.domain.content.entity.Content;
import com.easylead.easylead.domain.content.repository.ContentRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

  public Long findMaxPage(String isbn) {
      return contentRepository.findPageByISBN(isbn);
  }

  public List<Content> getPageContent(Long pageId, String isbn) {
      List<Long> pageIds = new ArrayList<>();
      if(pageId%2==0){
        pageIds.add(pageId-1);
        pageIds.add(pageId);
      }
      else{
        pageIds.add(pageId);
        pageIds.add(pageId+1);
      }

      return contentRepository.findContent(pageIds,isbn);
  }
}
