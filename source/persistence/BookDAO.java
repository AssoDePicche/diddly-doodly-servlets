package persistence;

import application.Container;

import domain.Book;
import domain.User;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public final class BookDAO implements DataAccessObject<Book, UUID> {
  private Connection connection;
  private UserDAO persistence;

  public BookDAO(Connection connection) {
    this.connection = connection;
    this.persistence = new UserDAO(connection);
  }

  @Override
  public void save(Book book) throws SQLException {
    String query = "{call SaveBook(?, ?, ?, ?, ?, ?, ?)}";

    try (CallableStatement statement = this.connection.prepareCall(query)) {
      statement.setString(1, book.getID().toString());

      statement.setString(2, book.getUser().getID().toString());

      statement.setString(3, book.getPublisher());

      statement.setString(4, book.getName());

      statement.setDouble(5, book.getCoverPrice());

      statement.setInt(6, book.getPageCount());

      statement.setDate(7, Date.valueOf(book.getPublishedAt()));

      statement.executeUpdate();
    }
  }

  @Override
  public void update(Book book) throws SQLException {
    String query = "{call UpdateBook(?, ?, ?, ?, ?, ?, ?)}";

    try (CallableStatement statement = this.connection.prepareCall(query)) {
      statement.setString(1, book.getID().toString());

      statement.setString(2, book.getUser().getID().toString());

      statement.setString(3, book.getPublisher());

      statement.setString(4, book.getName());

      statement.setDouble(5, book.getCoverPrice());

      statement.setInt(6, book.getPageCount());

      statement.setDate(7, Date.valueOf(book.getPublishedAt()));

      statement.executeUpdate();
    }
  }

  @Override
  public void remove(Book book) throws SQLException {
    String query = "{call RemoveBook(?)}";

    try (CallableStatement statement = this.connection.prepareCall(query)) {
      statement.setString(1, book.getID().toString());

      statement.executeUpdate();
    }
  }

  @Override
  public Collection<Book> fetch() throws SQLException {
    String query = "{call QueryBooks()}";

    Collection<Book> books = new ArrayList<>();

    try (CallableStatement statement = this.connection.prepareCall(query)) {
      try (ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()) {
          books.add(from(resultSet));
        }
      }
    }

    return books;
  }

  public Collection<Book> fetch(User user) throws SQLException {
    String query = "{call QueryUserBooks(?)}";

    Collection<Book> books = new ArrayList<>();

    try (CallableStatement statement = this.connection.prepareCall(query)) {
      statement.setString(1, user.getID().toString());

      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          books.add(from(resultSet));
        }
      }
    }

    return books;
  }

  @Override
  public Optional<Book> fetch(UUID id) throws SQLException {
    String query = "{call QueryBook(?)}";

    try (CallableStatement statement = this.connection.prepareCall(query)) {
      statement.setString(1, id.toString());

      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return Optional.of(from(resultSet));
        }
      }
    }

    return Optional.empty();
  }

  private Book from(ResultSet resultSet) throws SQLException {
    Book book = new Book();

    book.setID(UUID.fromString(resultSet.getString("id")));

    book.setPublisher(resultSet.getString("publisher"));

    book.setName(resultSet.getString("name"));

    book.setCoverPrice(resultSet.getDouble("cover_price"));

    book.setPageCount(resultSet.getInt("page_count"));

    book.setPublishedAt(resultSet.getDate("published_at").toLocalDate());

    book.setUser(this.persistence.fetch(UUID.fromString(resultSet.getString("user"))).orElse(null));

    return book;
  }
}
