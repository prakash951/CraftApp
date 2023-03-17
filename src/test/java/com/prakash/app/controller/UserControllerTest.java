package com.prakash.app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.prakash.app.dto.request.UserRequest;
import com.prakash.app.dto.response.Response;
import com.prakash.app.dto.response.UserResponse;
import com.prakash.app.service.UserService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  @Mock UserService myUserService;

  @InjectMocks UserController myUserController;

  @Test
  void createUserTest() {
    UserRequest myUserRequest = new UserRequest();
    myUserRequest.setCarName("bmw");
    myUserRequest.setCarNumber("AP39LP3452");
    myUserRequest.setPassword("search");
    myUserRequest.setConfirmPassword("search");
    myUserRequest.setEmail("a@a.com");
    myUserRequest.setFirstName("abc");
    myUserRequest.setLastName("def");
    myUserRequest.setPhoneNumber("8765456789");

    UserResponse myUserResponse = new UserResponse();

    Optional<UserResponse> response = Optional.of(myUserResponse);

    when(myUserService.createUser(myUserRequest)).thenReturn(response);

    ResponseEntity<?> createResponse = myUserController.createUser("", myUserRequest);

    assertEquals(myUserResponse, ((Response) createResponse.getBody()).getResponse());
  }

  @Test
  void createUserTest1() {
    UserRequest myUserRequest = new UserRequest();
    myUserRequest.setCarName("bmw");
    myUserRequest.setCarNumber("AP39LP3452");
    myUserRequest.setPassword("search");
    myUserRequest.setConfirmPassword("search");
    myUserRequest.setEmail("a@a.com");
    myUserRequest.setFirstName("abc");
    myUserRequest.setLastName("def");
    myUserRequest.setPhoneNumber("8765456789");

    Optional<UserResponse> response = Optional.empty();

    when(myUserService.createUser(myUserRequest)).thenReturn(response);

    ResponseEntity<?> createResponse = myUserController.createUser("", myUserRequest);

    assertEquals(HttpStatus.NOT_ACCEPTABLE, createResponse.getStatusCode());
  }

  @Test
  void createUserTest2() {
    UserRequest myUserRequest = new UserRequest();
    myUserRequest.setCarName("bmw");
    myUserRequest.setCarNumber("AP39LP3452");
    myUserRequest.setPassword("search");
    myUserRequest.setConfirmPassword("search1");
    myUserRequest.setEmail("a@a.com");
    myUserRequest.setFirstName("abc");
    myUserRequest.setLastName("def");
    myUserRequest.setPhoneNumber("8765456789");

    ResponseEntity<?> createResponse = myUserController.createUser("", myUserRequest);

    assertEquals(HttpStatus.BAD_REQUEST, createResponse.getStatusCode());
  }

  @Test
  void deleteUserTest1() {}
}
