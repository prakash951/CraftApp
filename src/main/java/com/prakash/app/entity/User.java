package com.prakash.app.entity;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "user", schema = "public")
public class User {

  @Id
  @Column(nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  List<Address> address;

  @Column(name = "firstname")
  String firstName;

  @Column(name = "lastname")
  String lastName;

  @Column(name = "phonenumber")
  String phoneNumber;

  @Column(name = "email")
  String email;

  @Column(name = "password")
  String password;

  @Column(name = "confirmpassword")
  String confirmPassword;

  @Column(name = "carnumber")
  String carNumber;

  @Column(name = "carname")
  String carName;

  @Column(name = "enabled")
  boolean enabled;

  @Column(name = "createdby")
  String createdBy;

  @Column(name = "createdon")
  LocalDateTime createdOn;

  @Column(name = "uid")
  String uid;
}
