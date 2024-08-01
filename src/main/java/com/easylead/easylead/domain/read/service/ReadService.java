package com.easylead.easylead.domain.read.service;

import com.easylead.easylead.domain.read.entity.Read;
import com.easylead.easylead.domain.read.repository.ReadRepository;
import com.easylead.easylead.domain.users.entity.Users;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReadService {
  private final ReadRepository readRepository;

  public List<Read> findByUsers(Users user) {
    return readRepository.findByUser(user);
  }
}
