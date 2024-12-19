package domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public final class User {
  private UUID id;
  private String username;
  private String email;
  private String password;
  private Collection<Book> books = new ArrayList<>();

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

  public Collection getBooks() {
    return this.books;
  }

  public void add(Book book) {
    this.books.add(book);
  }
}
