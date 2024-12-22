package domain;

import java.time.LocalDate;

import java.util.UUID;

public final class Book {
  private UUID id;
  private User user;
  private String publisher;
  private String name;
  private double coverPrice;
  private int pageCount;
  private LocalDate publishedAt;

  public UUID getID() {
    return this.id;
  }

  public void setID(UUID id) {
    this.id = id;
  }

  public String getPublisher() {
    return this.publisher;
  }

  public void setPublisher(String publisher) throws IllegalArgumentException {
    if (publisher.isEmpty() || publisher.length() > 255) {
      throw new IllegalArgumentException("Publisher must be up to 255 characters length");
    }

    this.publisher = publisher;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) throws IllegalArgumentException {
    if (name.isEmpty() || name.length() > 255) {
      throw new IllegalArgumentException("Name must be up to 255 characters length");
    }

    this.name = name;
  }

  public double getCoverPrice() {
    return this.coverPrice;
  }

  public void setCoverPrice(double coverPrice) throws IllegalArgumentException {
    if (coverPrice < 0.0) {
      throw new IllegalArgumentException("Illegal cover price");
    }

    this.coverPrice = coverPrice;
  }

  public int getPageCount() {
    return this.pageCount;
  }

  public void setPageCount(int pageCount) throws IllegalArgumentException {
    if (pageCount <= 0) {
      throw new IllegalArgumentException("Illegal page count");
    }

    this.pageCount = pageCount;
  }

  public LocalDate getPublishedAt() {
    return this.publishedAt;
  }

  public void setPublishedAt(LocalDate publishedAt) {
    this.publishedAt = publishedAt;
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "Book{"
        + "id="
        + id
        + ", user="
        + user
        + ", publisher='"
        + publisher
        + '\''
        + ", name='"
        + name
        + '\''
        + ", coverPrice="
        + coverPrice
        + ", pageCount="
        + pageCount
        + ", publishedAt="
        + publishedAt
        + '}';
  }
}
