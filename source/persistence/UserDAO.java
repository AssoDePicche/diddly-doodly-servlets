package persistence;

import application.Container;

import domain.Book;
import domain.User;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import persistence.BookDAO;

public final class UserDAO implements DataAccessObject<User, UUID> {
  @Override
  public boolean save(User user) {
    String query = "{call SaveUser(?, ?, ?, ?)}";

    try (Connection connection = Container.getConnection();
        CallableStatement statement = connection.prepareCall(query)) {
      user.setID(UUID.randomUUID());

      statement.setString(1, user.getID().toString());

      statement.setString(2, user.getUsername());

      statement.setString(3, user.getEmail());

      statement.setString(4, user.getPassword());

      statement.executeUpdate();

      return true;
    } catch (Exception exception) {
      exception.printStackTrace();
    }

    return false;
  }

  @Override
  public boolean update(User user) {
    String query = "{call UpdateUser(?, ?, ?)}";

    try (Connection connection = Container.getConnection();
        CallableStatement statement = connection.prepareCall(query)) {
      statement.setString(1, user.getID().toString());

      statement.setString(2, user.getUsername());

      statement.setString(3, user.getPassword());

      statement.executeUpdate();

      return true;
    } catch (Exception exception) {
      exception.printStackTrace();
    }

    return false;
  }

  @Override
  public boolean remove(User user) {
    String query = "{call RemoveUser(?)}";

    try (Connection connection = Container.getConnection();
        CallableStatement statement = connection.prepareCall(query)) {
      statement.setString(1, user.getID().toString());

      statement.executeUpdate();

      return true;
    } catch (Exception exception) {
      exception.printStackTrace();
    }

    return false;
  }

  @Override
  public Collection<User> fetch() {
    String query = "{call QueryUsers()}";

    Collection<User> users = new ArrayList<>();

    try (Connection connection = Container.getConnection();
        CallableStatement statement = connection.prepareCall(query);
        ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        users.add(from(resultSet));
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }

    return users;
  }

  @Override
  public Optional<User> fetch(UUID id) {
    String query = "{call QueryUser(?)}";

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

  public Optional<User> fetch(String username) {
    String query = "{call QueryUserByUsername(?)}";

    try (Connection connection = Container.getConnection();
        CallableStatement statement = connection.prepareCall(query)) {
      statement.setString(1, username);

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

  private User from(ResultSet resultSet) throws Exception {
    User user = new User();

    user.setID(UUID.fromString(resultSet.getString("id")));

    user.setUsername(resultSet.getString("username"));

    user.setEmail(resultSet.getString("email"));

    user.setPassword(resultSet.getString("password"));

    Collection<Book> books = new BookDAO().fetch(user);

    for (Book book : books) {
      user.add(book);
    }

    return user;
  }
}
