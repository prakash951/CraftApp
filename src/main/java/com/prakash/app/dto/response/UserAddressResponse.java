package com.prakash.app.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAddressResponse implements UserResponseInterface {

  UserResponse userResponse;
  List<AddressResponse> addressResponse;
}
