package com.easylead.easylead.domain.books.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Getter
public class Book {
    @Id
    private String ISBN;
    private String title;
    private String author;
    private String publisher;
    private String cover;
    private Date pubDate;
    private String description;
    private String categoryName;

}
