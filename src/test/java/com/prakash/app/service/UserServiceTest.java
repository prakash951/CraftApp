package com.prakash.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.prakash.app.dto.request.AddressRequest;
import com.prakash.app.dto.request.UserRequest;
import com.prakash.app.dto.response.UserAddressResponse;
import com.prakash.app.dto.response.UserResponse;
import com.prakash.app.entity.User;
import com.prakash.app.repository.AddressRepository;
import com.prakash.app.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock UserRepository myUserRepository;

  @Mock AddressRepository myAddressRepository;

  @Mock MinioService myMinioService;

  UserRequest myUserRequest;

  @InjectMocks UserService myUserService;

  @Test
  void createUserAlreadyExists() {

    myUserRequest =
        UserRequest.builder()
            .carName("jeep")
            .carNumber("KA03TP3421")
            .password("cross")
            .confirmPassword("cross")
            .phoneNumber("3452314567")
            .build();

    UUID myId = UUID.nameUUIDFromBytes(String.valueOf(Objects.hashCode(myUserRequest)).getBytes());
    String myUid = myId.toString();

    when(myUserRepository.findByUid(myUid)).thenReturn(Optional.of(new User()));

    Optional<UserResponse> response = myUserService.createUser(myUserRequest);
    assertEquals(false, response.isPresent());
  }

  @Test
  void createNewUser() {

    myUserRequest =
        UserRequest.builder()
            .carName("jeep")
            .carNumber("KA03TP3421")
            .password("cross")
            .confirmPassword("cross")
            .phoneNumber("3452314567")
            .build();

    UUID myId = UUID.nameUUIDFromBytes(String.valueOf(Objects.hashCode(myUserRequest)).getBytes());
    String myUid = myId.toString();

    User user = new User();
    user.setUid(myUid);

    when(myUserRepository.findByUid(myUid)).thenReturn(Optional.empty());

    when(myUserRepository.save(any())).thenReturn(user);

    Optional<UserResponse> response = myUserService.createUser(myUserRequest);
    assertEquals(true, response.isPresent());
    assertEquals(myUid, response.get().getId());
  }

  @Test
  void updateUserThrowsException() {

    myUserRequest =
        UserRequest.builder()
            .carName("jeep")
            .carNumber("KA03TP3421")
            .password("cross")
            .confirmPassword("cross")
            .phoneNumber("3452314567")
            .enabled(true)
            .build();

    UUID myId = UUID.nameUUIDFromBytes(String.valueOf(Objects.hashCode(myUserRequest)).getBytes());
    String myUid = myId.toString();

    User user = new User();
    user.setId(1l);
    user.setUid(myUid);

    when(myUserRepository.findByUid(myUid)).thenReturn(Optional.of(user));

    assertThrows(RuntimeException.class, () -> myUserService.updateUser(myUid, myUserRequest));
  }

  @Test
  void updateUser() {

    myUserRequest =
        UserRequest.builder()
            .carName("jeep")
            .carNumber("KA03TP3421")
            .password("cross")
            .confirmPassword("cross")
            .phoneNumber("3452314567")
            .enabled(true)
            .email("a@a.com")
            .firstName("a")
            .lastName("b")
            .build();

    UUID myId = UUID.nameUUIDFromBytes(String.valueOf(Objects.hashCode(myUserRequest)).getBytes());
    String myUid = myId.toString();

    User user = new User();
    user.setId(1l);
    user.setUid(myUid);
    user.setPhoneNumber("3452314567");
    when(myUserRepository.findByUid(myUid)).thenReturn(Optional.of(user));
    when(myUserRepository.save(any())).thenReturn(user);

    Optional<?> response = myUserService.updateUser(myUid, myUserRequest);
    assertEquals(true, response.isPresent());
    assertEquals(true, ((UserResponse) response.get()).getEnabled());
  }

  @Test
  void updateUser1() {

    myUserRequest =
        UserRequest.builder()
            .carName("jeep")
            .carNumber("KA03TP3421")
            .password("cross")
            .confirmPassword("cross")
            .phoneNumber("3452314567")
            .enabled(true)
            .build();

    UUID myId = UUID.nameUUIDFromBytes(String.valueOf(Objects.hashCode(myUserRequest)).getBytes());
    String myUid = myId.toString();

    User user = new User();
    user.setId(1l);
    user.setUid(myUid);
    user.setPhoneNumber("3452314567");
    when(myUserRepository.findByUid(myUid)).thenReturn(Optional.of(user));
    when(myUserRepository.save(any())).thenReturn(user);

    Optional<?> response = myUserService.updateUser(myUid, myUserRequest);
    assertEquals(true, response.isPresent());
    assertEquals(true, ((UserResponse) response.get()).getEnabled());
  }

  @Test
  void createNewUser1() {

    myUserRequest =
        UserRequest.builder()
            .carName("jeep")
            .carNumber("KA03TP3421")
            .password("cross")
            .confirmPassword("cross")
            .phoneNumber("3452314567")
            .build();
    List<AddressRequest> list = new ArrayList<>();
    list.add(
        AddressRequest.builder()
            .state("KA")
            .city("Bangalore")
            .flatno("ff 202")
            .name("Prak way enclave")
            .build());
    myUserRequest.setAddress(list);
    UUID myId = UUID.nameUUIDFromBytes(String.valueOf(Objects.hashCode(myUserRequest)).getBytes());
    String myUid = myId.toString();

    User user = new User();
    user.setUid(myUid);

    when(myUserRepository.findByUid(myUid)).thenReturn(Optional.empty());

    when(myUserRepository.save(any())).thenReturn(user);

    Optional<UserResponse> response = myUserService.createUser(myUserRequest);
    assertEquals(true, response.isPresent());
    assertEquals(myUid, response.get().getId());
  }

  @Test
  void getAllUsers() {

    UUID myId = UUID.nameUUIDFromBytes(String.valueOf(Objects.hashCode(myUserRequest)).getBytes());
    String myUid = myId.toString();

    User user = new User();
    user.setUid(myUid);

    List<User> list = new ArrayList<>();
    list.add(user);

    when(myUserRepository.findAll()).thenReturn(list);

    List<UserResponse> response = myUserService.getAllUser();
    assertEquals(1, response.size());
  }

  @Test
  void getSingleUsersThrowsException() {

    UUID myId = UUID.nameUUIDFromBytes(String.valueOf(Objects.hashCode(myUserRequest)).getBytes());
    String myUid = myId.toString();

    User user = new User();
    user.setUid(myUid);

    List<User> list = new ArrayList<>();
    list.add(user);

    when(myUserRepository.findByUid(myUid)).thenReturn(Optional.of(user));

    assertThrows(RuntimeException.class, () -> myUserService.getUser(myUid));
  }

  @Test
  void getSingleUsers() {

    UUID myId = UUID.nameUUIDFromBytes(String.valueOf(Objects.hashCode(myUserRequest)).getBytes());
    String myUid = myId.toString();

    User user = new User();
    user.setUid(myUid);
    user.setAddress(new ArrayList<>());
    List<User> list = new ArrayList<>();
    list.add(user);

    when(myUserRepository.findByUid(myUid)).thenReturn(Optional.of(user));

    Optional<UserAddressResponse> response = myUserService.getUser(myUid);

    assertTrue(response.isPresent());
  }

  @Test
  void getSingleUsers1() {

    UUID myId = UUID.nameUUIDFromBytes(String.valueOf(Objects.hashCode(myUserRequest)).getBytes());
    String myUid = myId.toString();

    User user = new User();
    user.setUid(myUid);

    List<User> list = new ArrayList<>();
    list.add(user);

    when(myUserRepository.findByUid(myUid)).thenReturn(Optional.empty());

    Optional<UserAddressResponse> response = myUserService.getUser(myUid);
    assertEquals(true, response.isEmpty());
  }
}
