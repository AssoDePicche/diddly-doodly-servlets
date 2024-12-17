package domain;

import java.util.regex.Pattern;

import java.util.UUID;

public final class User {
  private static String regex =
      "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

  private static Pattern pattern = Pattern.compile(regex);

  private UUID id;
  private String username;
  private String email;
  private String password;

  public User() {}

  public UUID getID() {
    return this.id;
  }

  public void setID(UUID id) {
    this.id = id;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) throws IllegalArgumentException {
    if (username == null || username.isEmpty() || username.length() > 32) {
      throw new IllegalArgumentException(
          "A username must contain at least one character and not exceed 32 characters");
    }

    this.username = username;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) throws IllegalArgumentException {
    if (email == null
        || email.isEmpty()
        || email.length() > 255
        || !pattern.matcher(email).matches()) {
      throw new IllegalArgumentException(
          "A email address consists of an email prefix and an email domain");
    }

    this.email = email;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) throws IllegalArgumentException {
    if (password == null || password.length() < 8 || password.length() > 32) {
      throw new IllegalArgumentException("A password  must contain at least 8 characters");
    }

    this.password = password;
  }
}
