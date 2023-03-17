package com.prakash.app.repository;

import com.prakash.app.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUid(String uid);

  Integer deleteByUid(String uid);
}
