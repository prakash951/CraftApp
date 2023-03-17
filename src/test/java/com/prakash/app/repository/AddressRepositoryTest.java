package com.prakash.app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AddressRepositoryTest {

  @Autowired UserRepository myUserRepository;
}
