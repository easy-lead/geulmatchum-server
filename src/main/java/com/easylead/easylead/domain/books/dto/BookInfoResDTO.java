package com.easylead.easylead.domain.books.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookInfoResDTO {
    @JsonProperty("isbn")
    private String ISBN;
    @JsonProperty("title")
    private String title;
    @JsonProperty("author")
    private String author;
    @JsonProperty("publisher")
    private String publisher;
    @JsonProperty("cover")
    private String cover;
    private Date pubDate;
    @JsonProperty("description")
    private String description;
    @JsonProperty("categoryName")
    private String categoryName;
    private String status;
}
