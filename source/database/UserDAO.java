package com.assodepicche.infrastructure;

import com.assodepicche.domain.Email;
import com.assodepicche.domain.Password;
import com.assodepicche.domain.User;
import com.assodepicche.domain.Username;

import com.assodepicche.shared.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public final class UserDAO extends DataAccessObject<User, UUID> {
  public UserDAO(Connection connection) {
    super(connection);
  }

  @Override
  public void save(User user) throws SQLException {
    String query = "INSERT INTO Users (id, username, email, password) VALUES (?, ?, ?, ?)";

    PreparedStatement statement = getConnection().prepareStatement(query);

    statement.setString(1, user.getID().toString());

    statement.setString(2, user.getUsername().toString());

    statement.setString(3, user.getEmail().toString());

    statement.setString(4, user.getPassword().toString());

    statement.executeUpdate();
  }

  @Override
  public void update(User user) throws SQLException {
    String query = "UPDATE Users SET username = ? SET password = ? WHERE id = ?";

    PreparedStatement statement = getConnection().prepareStatement(query);

    statement.setString(1, user.getUsername().toString());

    statement.setString(2, user.getPassword().toString());

    statement.setString(3, user.getID().toString());

    statement.executeUpdate();
  }

  @Override
  public void remove(User user) throws SQLException {
    String query = "UPDATE Users SET active = ? WHERE id = ?";

    PreparedStatement statement = getConnection().prepareStatement(query);

    statement.setBoolean(1, false);

    statement.setString(2, user.getID().toString());

    statement.executeUpdate();
  }

  @Override
  public Collection<User> fetch() throws SQLException {
    String query = "SELECT * FROM Users";

    PreparedStatement statement = getConnection().prepareStatement(query);

    ResultSet resultSet = statement.executeQuery();

    Collection<User> users = new ArrayList<>();

    while (resultSet.next()) {
      users.add(from(resultSet));
    }

    return users;
  }

  @Override
  public Optional<User> fetch(UUID id) throws SQLException {
    String query = "SELECT * FROM Users WHERE id = ?";

    PreparedStatement statement = getConnection().prepareStatement(query);

    statement.setString(1, id.toString());

    ResultSet resultSet = statement.executeQuery();

    return Optional.ofNullable(from(resultSet));
  }

  private User from(ResultSet resultSet) throws SQLException {
    User user = new User();

    try {
      user.setID(UUID.fromString(resultSet.getString("id")));

      user.setUsername(Username.from(resultSet.getString("username")));

      user.setEmail(Email.from(resultSet.getString("email")));

      user.setPassword(Password.from(resultSet.getString("password")));
    } catch (IllegalArgumentException exception) {
    }

    return user;
  }
}
