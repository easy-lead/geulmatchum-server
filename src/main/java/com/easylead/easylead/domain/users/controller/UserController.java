package com.easylead.easylead.domain.users.controller;

import com.easylead.easylead.domain.request.entity.Request;
import com.easylead.easylead.domain.users.business.UserBusiness;
import com.easylead.easylead.domain.users.dto.UserReadResDTO;
import com.easylead.easylead.domain.users.dto.UserRequestResDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

  private final UserBusiness userBusiness;

  @GetMapping("/read")
  public ResponseEntity<List<UserReadResDTO>> readBook(@RequestParam Long userId){

    return ResponseEntity.ok(userBusiness.readBook(userId));
  }

  @GetMapping("/request")
  public ResponseEntity<List<Request>> requestBook(@RequestParam Long userId){

    return ResponseEntity.ok(userBusiness.requestBook(userId));
  }
}
