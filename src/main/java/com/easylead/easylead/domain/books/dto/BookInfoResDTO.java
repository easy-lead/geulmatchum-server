package com.easylead.easylead.domain.books.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookInfoResDTO {
    private String ISBN;
    private String title;
    private String author;
    private String publisher;
    private String cover;
    private Date pubDate;
    private String description;
    private String categoryName;
    private String status;
}
