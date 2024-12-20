package domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public final class User {
  private UUID id;
  private String username;
  private String email;
  private String password;
  private Collection<Book> books = new HashSet<>();

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

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
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
