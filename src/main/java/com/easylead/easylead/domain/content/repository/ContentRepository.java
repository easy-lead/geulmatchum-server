package com.easylead.easylead.domain.content.repository;

import com.easylead.easylead.domain.content.entity.Content;
import com.easylead.easylead.domain.content.entity.ContentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, ContentId> {
}
