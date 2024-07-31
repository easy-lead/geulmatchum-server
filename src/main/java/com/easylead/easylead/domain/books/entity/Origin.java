package com.easylead.easylead.domain.books.entity;

import com.easylead.easylead.domain.request.entity.RequestId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Getter
public class Origin {
    @EmbeddedId
    private OriginId originId;

    @Column(columnDefinition = "text")
    private String pageContent;

}
