package com.easylead.easylead.domain.users.dto;

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
public class UserReadResDTO {
  private String ISBN;
  private String title;
  private String author;
  private String publisher;
  private String cover;
  private Long pageId;
  private LocalDateTime updateTime;

}
