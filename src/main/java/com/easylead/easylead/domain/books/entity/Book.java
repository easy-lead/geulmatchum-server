package com.easylead.easylead.domain.books.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.*;

import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private Long view;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;
}
