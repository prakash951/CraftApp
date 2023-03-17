package com.prakash.app.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AddressRequest {
  @NotBlank String type;
  @NotBlank String name;
  @NotBlank String flatno;
  @NotBlank String street;

  @Pattern(regexp = "[1-9]{1}[0-9]{4,5,6}")
  String pincode;

  @NotBlank String state;
  @NotBlank String country;
  @NotBlank String city;
}
