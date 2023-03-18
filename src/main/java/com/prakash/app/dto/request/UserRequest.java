package com.prakash.app.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"address"})
public class UserRequest {

  @NotBlank @NotEmpty String firstName;
  @NotBlank @NotEmpty String lastName;

  @Pattern(regexp = "[1-9]{1}[0-9]{8,9}")
  String phoneNumber;

  @NotBlank @NotEmpty @Email String email;

  @Size(max = 12)
  String password;

  @Size(max = 12)
  String confirmPassword;

  @Pattern(regexp = "[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}")
  String carNumber;

  @Size(max = 20)
  String carName;

  @JsonProperty("enabled")
  Boolean enabled;

  @JsonProperty("address")
  List<AddressRequest> address;
}
