package com.easylead.easylead.domain.users.business;

import com.easylead.easylead.common.annotation.Business;
import com.easylead.easylead.domain.read.converter.ReadConverter;
import com.easylead.easylead.domain.read.entity.Read;
import com.easylead.easylead.domain.read.service.ReadService;
import com.easylead.easylead.domain.request.converter.RequestConverter;
import com.easylead.easylead.domain.request.entity.Request;
import com.easylead.easylead.domain.request.service.RequestService;
import com.easylead.easylead.domain.users.dto.UserReadResDTO;
import com.easylead.easylead.domain.users.dto.UserRequestResDTO;
import com.easylead.easylead.domain.users.entity.Users;
import com.easylead.easylead.domain.users.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.convert.ReadingConverter;

@Business
@RequiredArgsConstructor
public class UserBusiness {

  private final UserService userService;
  private final ReadService readService;
  private final ReadConverter readConverter;
  private final RequestService requestService;
  private final RequestConverter requestConverter;

  public List<UserReadResDTO> readBook(Long userId) {
    Users user = userService.findById(userId);
    List<Read> readList= readService.findByUsers(user);
    return readList.stream().map(read -> readConverter.toUserReadResDTO(read.getBook(),read.getPage(), read.getUpdateAt())).toList();
  }

  public List<Request> requestBook(Long userId) {
    Users user = userService.findById(userId);
    List<Request> requestList = requestService.findByUser(user);

    return requestList;
  }
}
