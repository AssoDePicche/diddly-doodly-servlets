package persistence;

import application.Service;

import domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public final class UserDAO implements DataAccessObject<User, UUID> {
  private Connection connection = Service.getConnection();

  @Override
  public void save(User user) throws Exception {
    String query = "INSERT INTO Users (id, username, email, password) VALUES (?, ?, ?, ?)";

    try (PreparedStatement statement = this.connection.prepareStatement(query)) {
      user.setID(UUID.randomUUID());

      statement.setString(1, user.getID().toString());

      statement.setString(2, user.getUsername());

      statement.setString(3, user.getEmail());

      statement.setString(4, user.getPassword());

      statement.executeUpdate();
    }
  }

  @Override
  public void update(User user) throws Exception {
    String query = "UPDATE Users SET username = ? SET password = ? WHERE id = ? AND active = TRUE";

    try (PreparedStatement statement = this.connection.prepareStatement(query)) {

      statement.setString(1, user.getUsername());

      statement.setString(2, user.getPassword());

      statement.setString(3, user.getID().toString());

      statement.executeUpdate();
    }
  }

  @Override
  public void remove(User user) throws Exception {
    String query = "UPDATE Users SET active = ? WHERE id = ?";

    try (PreparedStatement statement = this.connection.prepareStatement(query)) {

      statement.setBoolean(1, false);

      statement.setString(2, user.getID().toString());

      statement.executeUpdate();
    }
  }

  @Override
  public Collection<User> fetch() throws Exception {
    String query = "SELECT * FROM Users WHERE active = TRUE";

    try (PreparedStatement statement = this.connection.prepareStatement(query)) {

      ResultSet resultSet = statement.executeQuery();

      Collection<User> users = new ArrayList<>();

      while (resultSet.next()) {
        users.add(from(resultSet));
      }

      return users;
    }
  }

  @Override
  public Optional<User> fetch(UUID id) throws Exception {
    String query = "SELECT * FROM Users WHERE id = ? AND active = TRUE";

    try (PreparedStatement statement = this.connection.prepareStatement(query)) {

      statement.setString(1, id.toString());

      ResultSet resultSet = statement.executeQuery();

      if (!resultSet.next()) {
        return Optional.empty();
      }

      return Optional.ofNullable(from(resultSet));
    }
  }

  public Optional<User> fetch(String username) throws Exception {
    String query = "SELECT * FROM Users WHERE username = ? AND active = TRUE";

    try (PreparedStatement statement = this.connection.prepareStatement(query)) {

      statement.setString(1, username);

      ResultSet resultSet = statement.executeQuery();

      if (!resultSet.next()) {
        return Optional.empty();
      }

      return Optional.ofNullable(from(resultSet));
    }
  }

  private User from(ResultSet resultSet) throws Exception {
    User user = new User();

    user.setID(UUID.fromString(resultSet.getString("id")));

    user.setUsername(resultSet.getString("username"));

    user.setEmail(resultSet.getString("email"));

    user.setPassword(resultSet.getString("password"));

    return user;
  }
}
