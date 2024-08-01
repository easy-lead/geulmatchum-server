package com.easylead.easylead.domain.read.repository;

import com.easylead.easylead.domain.read.entity.Read;
import com.easylead.easylead.domain.read.entity.ReadId;
import com.easylead.easylead.domain.users.entity.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReadRepository extends JpaRepository<Read, ReadId> {

  @Query("select r from Read r where r.readUser=:user")
  List<Read> findByUser(Users user);
}
