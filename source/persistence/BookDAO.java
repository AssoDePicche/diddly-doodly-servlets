package persistence;

import application.Container;

import domain.Book;
import domain.User;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public final class BookDAO implements DataAccessObject<Book, UUID> {
  @Override
  public boolean save(Book book) {
    String query = "{call SaveBook(?, ?, ?, ?, ?, ?, ?)}";

    try (Connection connection = Container.getConnection();
        CallableStatement statement = connection.prepareCall(query)) {
      statement.setString(1, book.getID().toString());

      statement.setString(2, book.getUser().getID().toString());

      statement.setString(3, book.getPublisher());

      statement.setString(4, book.getName());

      statement.setDouble(5, book.getCoverPrice());

      statement.setInt(6, book.getPageCount());

      statement.setDate(7, Date.valueOf(book.getPublishedAt()));

      statement.executeUpdate();

      return true;
    } catch (Exception exception) {
      exception.printStackTrace();
    }

    return false;
  }

  @Override
  public boolean update(Book book) {
    String query = "{call UpdateBook(?, ?, ?, ?, ?, ?, ?)}";

    try (Connection connection = Container.getConnection();
        CallableStatement statement = connection.prepareCall(query)) {
      statement.setString(1, book.getID().toString());

      statement.setString(2, book.getUser().getID().toString());

      statement.setString(3, book.getPublisher());

      statement.setString(4, book.getName());

      statement.setDouble(5, book.getCoverPrice());

      statement.setInt(6, book.getPageCount());

      statement.setDate(7, Date.valueOf(book.getPublishedAt()));

      statement.executeUpdate();

      return true;
    } catch (Exception exception) {
      exception.printStackTrace();
    }

    return false;
  }

  @Override
  public boolean remove(Book book) {
    String query = "{call RemoveBook(?)}";

    try (Connection connection = Container.getConnection();
        CallableStatement statement = connection.prepareCall(query)) {
      statement.setString(1, book.getID().toString());

      return statement.executeUpdate() != 1;
    } catch (Exception exception) {
      exception.printStackTrace();
    }

    return false;
  }

  @Override
  public Collection<Book> fetch() {
    String query = "{call QueryBooks()}";

    Collection<Book> books = new ArrayList<>();

    try (Connection connection = Container.getConnection();
        CallableStatement statement = connection.prepareCall(query);
        ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        books.add(from(resultSet));
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }

    return books;
  }

  public Collection<Book> fetch(User user) {
    String query = "{call QueryUserBooks(?)}";

    Collection<Book> books = new ArrayList<>();

    try (Connection connection = Container.getConnection();
        CallableStatement statement = connection.prepareCall(query)) {
      statement.setString(1, user.getID().toString());

      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          books.add(from(resultSet));
        }
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }

    return books;
  }

  @Override
  public Optional<Book> fetch(UUID id) {
    String query = "{call QueryBook(?)}";

    try (Connection connection = Container.getConnection();
        CallableStatement statement = connection.prepareCall(query)) {
      statement.setString(1, id.toString());

      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return Optional.of(from(resultSet));
        }
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }

    return Optional.empty();
  }

  private Book from(ResultSet resultSet) throws Exception {
    Book book = new Book();

    book.setID(UUID.fromString(resultSet.getString("id")));

    book.setPublisher(resultSet.getString("publisher"));

    book.setName(resultSet.getString("name"));

    book.setCoverPrice(resultSet.getDouble("cover_price"));

    book.setPageCount(resultSet.getInt("page_count"));

    book.setPublishedAt(resultSet.getDate("published_at").toLocalDate());

    book.setUser(new UserDAO().fetch(UUID.fromString(resultSet.getString("user"))).orElse(null));

    return book;
  }
}
