package com.prakash.app.util;

import com.prakash.app.dto.request.UserRequest;
import com.prakash.app.entity.User;
import java.util.ArrayList;
import java.util.List;

public class ValidateRequestUtil {

  public static List<String> validate(UserRequest request) {
    List<String> errors = new ArrayList<>();
    if (!request.getConfirmPassword().equals(request.getPassword())) {
      errors.add("Password and confirm password doesn't match");
    }
    if (request.getPhoneNumber() == null) {
      errors.add("Phone Number is mandatory");
    }
    return errors;
  }

  public static List<String> validateUpdate(User user, UserRequest updateUser) {
    List<String> errors = new ArrayList<>();
    if (updateUser.getConfirmPassword() != null && updateUser.getPassword() != null) {
      if (!updateUser.getConfirmPassword().equals(updateUser.getPassword())) {
        errors.add("Password and confirm password doesn't match");
      }
    }
    if (updateUser.getPhoneNumber() != null
        && !user.getPhoneNumber().equals(updateUser.getPhoneNumber())) {
      errors.add("You can't update the phone number please contact the support");
    }
    return errors;
  }
}
