package com.easylead.easylead.domain.gpt.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
@JsonPropertyOrder({"model", "prompt", "n","size"})
public class DalleRequestDTO {
  String model;
  String prompt;
  int n;
  String size;

}
