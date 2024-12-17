package domain;

import java.time.LocalDate;

import java.util.UUID;

public final class Book {
  private UUID id;
  private String publisher;
  private String name;
  private Double coverPrice;
  private int pageCount;
  private LocalDate publishedAt;

  public Book() {}

  public UUID getID() {
    return this.id;
  }

  public void setID(UUID id) {
    this.id = id;
  }

  public String getPublisher() {
    return this.publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getCoverPrice() {
    return this.coverPrice;
  }

  public void setCoverPrice(Double coverPrice) {
    this.coverPrice = coverPrice;
  }

  public int getPageCount() {
    return this.pageCount;
  }

  public void setPageCount(int pageCount) {
    this.pageCount = pageCount;
  }

  public LocalDate getPublishedAt() {
    return this.publishedAt;
  }

  public void setPublishedAt(LocalDate publishedAt) {
    this.publishedAt = publishedAt;
  }
}
