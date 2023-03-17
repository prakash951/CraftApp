package com.prakash.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponse {

  String type;
  String name;
  String flatno;
  String street;
  String pincode;
  String state;
  String city;
  String country;
}
