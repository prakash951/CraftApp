package com.prakash.app.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.prakash.app.entity.User;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

class UserRepositoryTest {

  //  @Autowired private TestEntityManager entityManager;

  @Autowired UserRepository myUserRepository;

  //  @Test
  void findByID() {
    User newUser = new User();
    newUser.setId(1l);
    //    this.entityManager.persist(newUser);
    Optional<User> user = myUserRepository.findById(1l);
    assertEquals(false, user.isEmpty());
  }
}
