package com.prakash.app.service;

import com.prakash.app.dto.request.AddressRequest;
import com.prakash.app.dto.request.UserRequest;
import com.prakash.app.dto.response.AddressResponse;
import com.prakash.app.dto.response.UserAddressResponse;
import com.prakash.app.dto.response.UserResponse;
import com.prakash.app.entity.Address;
import com.prakash.app.entity.User;
import com.prakash.app.repository.AddressRepository;
import com.prakash.app.repository.UserRepository;
import com.prakash.app.util.ValidateRequestUtil;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserService {

  @Autowired UserRepository myUserRepository;

  @Autowired AddressRepository myAddressRepository;

  @Transactional
  public Optional<UserResponse> createUser(UserRequest request) {
    log.info("Inside create user");
    UUID myId = UUID.nameUUIDFromBytes(String.valueOf(Objects.hashCode(request)).getBytes());
    String myUid = myId.toString();
    Optional<User> user = myUserRepository.findByUid(myUid);
    if (!user.isPresent()) {
      User newUser = new User();
      BeanUtils.copyProperties(request, newUser);
      newUser.setUid(myUid);
      newUser.setPassword(getDigest(request.getPassword()));
      newUser.setConfirmPassword(getDigest(request.getPassword()));
      myUserRepository.save(newUser);
      if (request.getAddress() != null)
        request.getAddress().stream()
            .map(addres -> createAddress(addres, newUser))
            .collect(Collectors.toList());
      Optional<UserResponse> response = createSuccessResponse(newUser);
      return response;
    }
    return Optional.empty();
  }

  @Transactional(propagation = Propagation.MANDATORY)
  private Address createAddress(AddressRequest addres, User user) {
    Address address = new Address();
    BeanUtils.copyProperties(addres, address);
    address.setUser(user);
    myAddressRepository.save(address);
    return address;
  }

  @Transactional
  public Optional<?> updateUser(String uid, UserRequest request) {
    log.info("Inside update user");
    Optional<User> user = myUserRepository.findByUid(uid);
    if (user.isPresent()) {
      User existingUser = user.get();
      List<String> errors = ValidateRequestUtil.validateUpdate(existingUser, request);
      if (errors.size() > 0) {
        log.info("Erros in the rquest " + errors);
        return Optional.of(errors);
      }
      updateUser(request, existingUser);
      myUserRepository.save(existingUser);
      if (request.getAddress() != null)
        request.getAddress().stream()
            .map(addres -> updateAddress(addres, existingUser))
            .collect(Collectors.toList());
      Optional<UserResponse> response = createSuccessResponse(existingUser);
      return response;
    }
    return Optional.empty();
  }

  @Transactional(propagation = Propagation.MANDATORY)
  private Address updateAddress(AddressRequest addres, User user) {
    Optional<Address> typeOfAddress =
        user.getAddress().stream()
            .filter(res -> res.getType().equalsIgnoreCase(addres.getType()))
            .findFirst();
    Address address = typeOfAddress.isPresent() ? typeOfAddress.get() : new Address();
    BeanUtils.copyProperties(addres, address);
    address.setUser(user);
    myAddressRepository.save(address);
    return address;
  }

  /**
   * @param request
   * @param existingUser
   */
  @Transactional(propagation = Propagation.MANDATORY)
  void updateUser(UserRequest request, User existingUser) {
    if (request.getPassword() != null) {
      existingUser.setPassword(getDigest(request.getPassword()));
      existingUser.setConfirmPassword(getDigest(request.getPassword()));
    }
    if (request.getCarName() != null) {
      existingUser.setCarName(request.getCarName());
    }
    if (request.getEmail() != null) {
      existingUser.setEmail(request.getEmail());
    }
    if (request.getCarNumber() != null) {
      existingUser.setCarNumber(request.getCarNumber());
    }
    if (request.getFirstName() != null) {
      existingUser.setFirstName(request.getFirstName());
    }
    if (request.getLastName() != null) {
      existingUser.setLastName(request.getLastName());
    }
  }

  private Optional<UserResponse> createSuccessResponse(User user) {
    log.info("Inside create success response");
    UserResponse response = new UserResponse();
    BeanUtils.copyProperties(user, response);
    response.setId(String.valueOf(user.getUid()));
    response.setName(getName(user));
    return Optional.of(response);
  }

  @Transactional
  public Optional<?> deleteUser(String uid) {
    log.info("Inside delete user");
    int deleted = myUserRepository.deleteByUid(uid);
    if (deleted > 0) {
      return Optional.of("User with uid :" + uid + " deleted. ");
    }
    return Optional.empty();
  }

  private String getName(User user) {
    return user.getFirstName() + " " + user.getLastName();
  }

  private String getDigest(String password) {
    log.info("Creating the digest for given password");
    MessageDigest md;
    byte[] hash = new byte[] {};
    try {
      md = MessageDigest.getInstance("sha-256");
      hash = md.digest(password.getBytes());
    } catch (NoSuchAlgorithmException e) {
      log.error("Error occured " + e.getLocalizedMessage());
    }
    return new String(hash, StandardCharsets.UTF_16);
  }

  public List<UserResponse> getAllUser(Integer pageNumber, Integer pageSize) {
    if (pageNumber != null && pageSize != null) {
      log.info("Inside get all users");
      log.info("Requesting users of page:" + pageNumber);
      PageRequest pageRequest = PageRequest.of(Math.max(0, pageNumber - 1), pageSize);
      Page<User> users = myUserRepository.findAll(pageRequest);
      List<UserResponse> userResponses =
          users.stream()
              .map(user -> createSuccessResponse(user))
              .map(user -> user.get())
              .collect(Collectors.toList());
      return userResponses;
    }
    return getAllUser();
  }

  public List<UserResponse> getAllUser() {
    log.info("Inside get all users");
    List<User> users = myUserRepository.findAll();
    List<UserResponse> userResponses =
        users.stream()
            .map(user -> createSuccessResponse(user))
            .map(user -> user.get())
            .collect(Collectors.toList());
    return userResponses;
  }

  public Optional<UserAddressResponse> getUser(String uid) {
    log.info("Inside get user");
    Optional<User> users = myUserRepository.findByUid(uid);

    if (users.isPresent()) {
      User user = users.get();
      Optional<UserResponse> userResponse = createSuccessResponse(user);
      List<AddressResponse> addresses =
          user.getAddress().stream()
              .map(
                  address -> {
                    AddressResponse response = new AddressResponse();
                    BeanUtils.copyProperties(address, response);
                    return response;
                  })
              .collect(Collectors.toList());
      UserAddressResponse myResponse = new UserAddressResponse(userResponse.get(), addresses);
      return Optional.of(myResponse);
    }
    return Optional.empty();
  }
}
