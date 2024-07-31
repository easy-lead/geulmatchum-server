package com.easylead.easylead.domain.request.repository;

import com.easylead.easylead.domain.request.entity.Request;
import com.easylead.easylead.domain.request.entity.RequestId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, RequestId> {

    @Query("select r from Request r where r.requestId.ISBN =:isbn")
    List<Request> findByRequestIdISBN(String isbn);
}
