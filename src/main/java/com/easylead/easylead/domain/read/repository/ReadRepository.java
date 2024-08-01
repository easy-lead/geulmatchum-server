package com.easylead.easylead.domain.read.repository;

import com.easylead.easylead.domain.read.entity.Read;
import com.easylead.easylead.domain.read.entity.ReadId;
import com.easylead.easylead.domain.users.entity.Users;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReadRepository extends JpaRepository<Read, ReadId> {

  @Query("select r from Read r where r.readUser=:user")
  List<Read> findByUser(Users user);

  @Query("select r from Read r where r.readId.ISBN =:isbn and r.readId.userId =:userId")
  Read findByUserAndISBN(Long userId, String isbn);
}
