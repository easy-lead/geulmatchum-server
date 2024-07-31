package com.easylead.easylead.domain.content.entity;

import com.easylead.easylead.domain.books.entity.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor()
@AllArgsConstructor
@Entity
@Getter
@Builder

public class Content {
    @EmbeddedId
    private ContentId contentId;

    @ManyToOne(fetch = LAZY)
    @MapsId("ISBN")
    @JoinColumn(name = "ISBN")
    private Book book;

    @Column(columnDefinition = "text")
    private String pageContent;
    @Column(columnDefinition = "text")
    private String pageImg;
}
