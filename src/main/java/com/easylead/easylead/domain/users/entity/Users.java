package com.easylead.easylead.domain.users.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Getter
public class Users {
    @Id
    private Long userId;
    private String name;
    private String email;
    private String password;
}
