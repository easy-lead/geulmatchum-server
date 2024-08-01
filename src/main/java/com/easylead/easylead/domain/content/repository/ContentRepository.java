package com.easylead.easylead.domain.content.repository;

import com.easylead.easylead.domain.content.entity.Content;
import com.easylead.easylead.domain.content.entity.ContentId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContentRepository extends JpaRepository<Content, ContentId> {

  @Query("select max(c.contentId.pageId) from Content c where c.contentId.ISBN =:isbn ")
  public Long findPageByISBN(String isbn);

  @Query("select c from Content c where c.contentId.ISBN =:isbn and c.contentId.pageId in (:pageIds)")
  List<Content> findContent(@Param("pageIds") List<Long> pageIds, @Param("isbn") String isbn);
}
