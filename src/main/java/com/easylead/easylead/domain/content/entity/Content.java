package com.easylead.easylead.domain.content.entity;

import com.easylead.easylead.domain.books.entity.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor()
@AllArgsConstructor
@Entity
@Getter
public class Content {
    @EmbeddedId
    private ContentId contentId;

    @ManyToOne(fetch = LAZY)
    @MapsId("ISBN")
    @JoinColumn(name = "ISBN")
    private Book book;

    private String pageContent;
    private String pageImg;
}
