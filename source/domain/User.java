package com.assodepicche.domain;

import java.util.UUID;

public final class User {
  private UUID id;
  private Username username;
  private Email email;
  private Password password;

  public User() {}

  public User(Username username, Email email, Password password) {
    this.id = UUID.randomUUID();

    this.username = username;

    this.email = email;

    this.password = password;
  }

  public UUID getID() {
    return this.id;
  }

  public void setID(UUID id) {
    this.id = id;
  }

  public Username getUsername() {
    return this.username;
  }

  public void setUsername(Username username) {
    this.username = username;
  }

  public Email getEmail() {
    return this.email;
  }

  public void setEmail(Email email) {
    this.email = email;
  }

  public Password getPassword() {
    return this.password;
  }

  public void setPassword(Password password) {
    this.password = password;
  }
}
