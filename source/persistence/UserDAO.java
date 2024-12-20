package persistence;

import domain.Book;
import domain.User;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import persistence.BookDAO;

public final class UserDAO implements DataAccessObject<User, UUID> {
  private Connection connection;
  private BookDAO persistence;

  public UserDAO(Connection connection) {
    this.connection = connection;

    this.persistence = new BookDAO(connection);
  }

  @Override
  public void save(User user) throws SQLException {
    String query = "{call SaveUser(?, ?, ?, ?)}";

    try (CallableStatement statement = this.connection.prepareCall(query)) {
      user.setID(UUID.randomUUID());

      statement.setString(1, user.getID().toString());

      statement.setString(2, user.getUsername());

      statement.setString(3, user.getEmail());

      statement.setString(4, user.getPassword());

      statement.executeUpdate();
    }
  }

  @Override
  public void update(User user) throws SQLException {
    String query = "{call UpdateUser(?, ?, ?)}";

    try (CallableStatement statement = this.connection.prepareCall(query)) {
      statement.setString(1, user.getID().toString());

      statement.setString(2, user.getUsername());

      statement.setString(3, user.getPassword());

      statement.executeUpdate();
    }
  }

  @Override
  public void remove(User user) throws SQLException {
    String query = "{call RemoveUser(?)}";

    try (CallableStatement statement = this.connection.prepareCall(query)) {
      statement.setString(1, user.getID().toString());

      statement.executeUpdate();
    }
  }

  @Override
  public Collection<User> fetch() throws SQLException {
    String query = "{call QueryUsers()}";

    Collection<User> users = new ArrayList<>();

    try (CallableStatement statement = this.connection.prepareCall(query)) {
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          users.add(from(resultSet));
        }
      }
    }

    return users;
  }

  @Override
  public Optional<User> fetch(UUID id) throws SQLException {
    String query = "{call QueryUser(?)}";

    try (CallableStatement statement = this.connection.prepareCall(query)) {
      statement.setString(1, id.toString());

      try (ResultSet resultSet = statement.executeQuery()) {

        if (resultSet.next()) {
          return Optional.of(from(resultSet));
        }
      }

      return Optional.empty();
    }
  }

  public Optional<User> fetch(String username) throws SQLException {
    String query = "{call QueryUserByUsername(?)}";

    try (CallableStatement statement = this.connection.prepareCall(query)) {
      statement.setString(1, username);

      try (ResultSet resultSet = statement.executeQuery()) {

        if (resultSet.next()) {
          return Optional.of(from(resultSet));
        }
      }

      return Optional.empty();
    }
  }

  private User from(ResultSet resultSet) throws SQLException {
    User user = new User();

    user.setID(UUID.fromString(resultSet.getString("id")));

    user.setUsername(resultSet.getString("username"));

    user.setEmail(resultSet.getString("email"));

    user.setPassword(resultSet.getString("password"));

    Collection<Book> books = this.persistence.fetch(user);

    for (Book book : books) {
      user.add(book);
    }

    return user;
  }
}
