package com.easylead.easylead.domain.books.repository;

import com.easylead.easylead.domain.books.entity.Origin;
import com.easylead.easylead.domain.books.entity.OriginId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OriginRepository extends JpaRepository<Origin, OriginId> {

    @Query("select o from Origin o where o.originId.ISBN =:isbn")
    Optional<List<Origin>> findByISBN( @Param("isbn")String isbn);
}
