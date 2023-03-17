package com.prakash.app.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.prakash.app.dto.request.UserRequest;
import com.prakash.app.entity.User;
import java.util.List;
import org.junit.jupiter.api.Test;

class ValidateRequestUtilTest {

  @Test
  void testValidate() {
    UserRequest request = new UserRequest();
    request.setPassword("abc");
    request.setConfirmPassword("bcd");
    request.setPhoneNumber(null);
    List<String> list = ValidateRequestUtil.validate(request);
    assertEquals(2, list.size());
  }

  @Test
  void testValidate1() {
    UserRequest request = new UserRequest();
    request.setPassword("abc");
    request.setConfirmPassword("bcd");
    request.setPhoneNumber("9019432343");
    List<String> list = ValidateRequestUtil.validate(request);
    assertEquals(1, list.size());
  }

  @Test
  void testValidate2() {
    UserRequest request = new UserRequest();
    request.setPassword("abc");
    request.setConfirmPassword("abc");
    request.setPhoneNumber("9019432343");
    List<String> list = ValidateRequestUtil.validate(request);
    assertEquals(0, list.size());
  }

  @Test
  void testValidateUpdate() {
    User user = new User();
    UserRequest updateUser = new UserRequest();
    updateUser.setPassword("abc");
    updateUser.setConfirmPassword("abc");
    List<String> list = ValidateRequestUtil.validateUpdate(user, updateUser);
    assertTrue(list.size() == 0);
  }

  @Test
  void testValidateUpdate1() {
    User user = new User();
    user.setPhoneNumber("345676789");
    UserRequest updateUser = new UserRequest();
    updateUser.setPassword("abc");
    updateUser.setConfirmPassword("abc");
    updateUser.setPhoneNumber("7643532345");
    List<String> list = ValidateRequestUtil.validateUpdate(user, updateUser);
    assertTrue(list.size() == 1);
  }
}
