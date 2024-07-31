package com.easylead.easylead.domain.request.entity;

import com.easylead.easylead.domain.books.entity.Book;
import com.easylead.easylead.domain.users.entity.Users;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Builder
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class Request {
    @EmbeddedId
    private RequestId requestId;

    @ManyToOne(fetch = LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private Users readUser;

    @Enumerated(EnumType.STRING)
    private Progress progress;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    private String title;
    private String cover;
    private String author;
    private String publisher;

}
