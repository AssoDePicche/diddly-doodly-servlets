package domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import java.util.regex.Pattern;

public final class User {
  private UUID id;
  private String username;
  private String email;
  private String password;
  private Collection<Book> books = new HashSet<>();

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
    if (username.isEmpty() || username.length() > 32) {
      throw new IllegalArgumentException("Username must be up to 32 characters length");
    }

    this.username = username;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) throws IllegalArgumentException {
    String regex =
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    Pattern pattern = Pattern.compile(regex);

    if (!pattern.matcher(email).matches()) {
      throw new IllegalArgumentException("Invalid email address");
    }

    this.email = email;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) throws IllegalArgumentException {
    if (password.length() < 8) {
      throw new IllegalArgumentException("Password must have at least 8 characters");
    }

    this.password = password;
  }

  public Collection<Book> getBooks() {
    return this.books;
  }

  public void add(Book book) {
    book.setUser(this);

    this.books.add(book);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    builder
        .append("User{")
        .append("id=")
        .append(id)
        .append(", username='")
        .append(username)
        .append('\'')
        .append(", email='")
        .append(email)
        .append('\'')
        .append(", password='")
        .append(password)
        .append('\'');

    builder.append(", books=[");

    for (Book book : books) {
      builder.append(book.toString()).append(", ");
    }

    if (!books.isEmpty()) {
      builder.setLength(builder.length() - 2);
    }

    builder.append(']');

    builder.append('}');

    return builder.toString();
  }
}
