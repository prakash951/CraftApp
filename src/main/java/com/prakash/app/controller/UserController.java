package com.prakash.app.controller;

import com.prakash.app.dto.request.UserRequest;
import com.prakash.app.dto.response.Response;
import com.prakash.app.dto.response.UserAddressResponse;
import com.prakash.app.dto.response.UserResponse;
import com.prakash.app.dto.response.UserResponseInterface;
import com.prakash.app.service.UserService;
import com.prakash.app.util.BuildResponse;
import com.prakash.app.util.ValidateRequestUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

  @Autowired UserService myUserService;

  private static final String SESSIONID = "SessionId";

  @PostMapping("/users")
  public ResponseEntity<?> createUser(
      @RequestHeader(required = false) String sessionId, @RequestBody @Valid UserRequest request) {

    log.info("Request received for create user " + request);
    MDC.put(SESSIONID, sessionId);
    List<String> errors = ValidateRequestUtil.validate(request);
    if (errors.size() > 0) {
      MDC.clear();
      return errorResponse(errors);
    }
    Optional<UserResponse> userResponse = myUserService.createUser(request);

    if (userResponse.isPresent()) {
      MDC.clear();
      return new ResponseEntity<>(
          BuildResponse.buildResponse(0, userResponse.get(), errors), HttpStatus.CREATED);
    }
    MDC.clear();
    return duplicateResponse(errors);
  }

  private ResponseEntity<?> duplicateResponse(List<String> errors) {
    return new ResponseEntity<>(
        BuildResponse.buildResponse(
            -1, UserResponse.builder().id("-1").name("Duplicate Request").build(), errors),
        HttpStatus.NOT_ACCEPTABLE);
  }

  private ResponseEntity<Response> errorResponse(List<String> errors) {
    return new ResponseEntity<>(
        BuildResponse.buildResponse(
            -1, UserResponse.builder().id("-1").name("Errors in the request").build(), errors),
        HttpStatus.BAD_REQUEST);
  }

  @SuppressWarnings("unchecked")
  @PutMapping("/users/{id}")
  public ResponseEntity<?> updateUser(
      @RequestHeader(required = false) String sessionId,
      @RequestBody UserRequest request,
      @PathVariable(name = "id") String uid) {
    log.info("Request received for update user " + request);
    MDC.put(SESSIONID, sessionId);
    Optional<?> userResponse = myUserService.updateUser(uid, request);
    if (userResponse.isPresent()) {
      if (userResponse.get() instanceof List) {
        MDC.clear();
        return errorResponse((List<String>) userResponse.get());
      }
      MDC.clear();
      return new ResponseEntity<>(
          BuildResponse.buildResponse(0, userResponse.get(), null), HttpStatus.OK);
    }
    MDC.clear();
    return new ResponseEntity<>(
        BuildResponse.buildResponse(-1, new UserResponse(), List.of("User Not Found")),
        HttpStatus.NOT_FOUND);
  }

  @GetMapping("/users")
  public ResponseEntity<List<UserResponse>> listUsers(
      @RequestHeader(required = false) String sessionId,
      @RequestHeader(required = false) Integer pageNumber,
      @RequestHeader(required = false) Integer pageSize) {
    Map<String, String> contextMap = new HashMap<>();
    contextMap.put(SESSIONID, sessionId);
    MDC.setContextMap(contextMap);
    log.info("Request received for list users");

    List<UserResponse> userResponse = myUserService.getAllUser(pageNumber, pageSize);
    if (userResponse != null && userResponse.size() > 0) {
      MDC.clear();
      return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
    MDC.clear();
    return new ResponseEntity<>(userResponse, HttpStatus.NO_CONTENT);
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<UserResponseInterface> getUser(
      @RequestHeader(required = false) String sessionId, @PathVariable(name = "id") String userId) {
    log.info("Request received for get a user with id " + userId);
    MDC.put(SESSIONID, sessionId);
    Optional<UserAddressResponse> response = myUserService.getUser(userId);
    if (response.isPresent()) {
      MDC.clear();
      return new ResponseEntity<>(response.get(), HttpStatus.OK);
    }
    MDC.clear();
    return new ResponseEntity<>(
        new UserAddressResponse(
            UserResponse.builder().id("-1").name("Requested Id doesn't exists").build(), null),
        HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<?> deleteUser(
      @RequestHeader(required = false) String sessionId, @PathVariable(name = "id") String userId) {
    log.info("Request received for delete user with id " + userId);
    MDC.put(SESSIONID, sessionId);
    Optional<?> myResponse = myUserService.deleteUser(userId);
    MDC.clear();
    if (myResponse.isPresent())
      return new ResponseEntity<>(
          BuildResponse.buildResponse(0, myResponse.get(), null), HttpStatus.OK);
    return new ResponseEntity<>(
        BuildResponse.buildResponse(0, myResponse, List.of("UserId doesn't exist: " + userId)),
        HttpStatus.NOT_FOUND);
  }
}
