package com.easylead.easylead.domain.books.entity;

import com.easylead.easylead.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Getter
public class Book extends BaseEntity {
    private String title;
    private String writer;
    private String publisher;
    private Date publicationDate;
    private String detail;

}
