package com.prakash.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse implements UserResponseInterface {

  private String id;
  private String name;
  private String email;
  private String phoneNumber;
  private String carName;
  private String carNumber;
}
