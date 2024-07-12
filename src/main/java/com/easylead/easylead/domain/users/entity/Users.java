package com.easylead.easylead.domain.users.entity;

import com.easylead.easylead.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Getter
public class Users extends BaseEntity {
    private String name;
    private String email;
    private String password;
}
