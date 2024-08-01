package com.easylead.easylead.domain.users.dto;

import com.easylead.easylead.domain.request.entity.Progress;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserRequestResDTO {
  private String ISBN;
  private String title;
  private String author;
  private String publisher;
  private String cover;
  private String progress;
  private LocalDateTime updateTime;

}
