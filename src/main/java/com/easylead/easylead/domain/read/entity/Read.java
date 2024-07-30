package com.easylead.easylead.domain.read.entity;

import com.easylead.easylead.domain.books.entity.Book;
import com.easylead.easylead.domain.users.entity.Users;
import jakarta.persistence.*;
import lombok.*;
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
public class Read {
    @EmbeddedId
    private ReadId readId;

    @ManyToOne(fetch = LAZY)
    @MapsId("userId")
    @JoinColumn(name = "read_user_id")
    private Users readUser;

    @ManyToOne(fetch = LAZY)
    @MapsId("ISBN")
    @JoinColumn(name = "ISBN")
    private Book book;

    private Long page;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;
}
