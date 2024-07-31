package com.easylead.easylead.domain.users.repository;

import com.easylead.easylead.domain.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Long> {
}
