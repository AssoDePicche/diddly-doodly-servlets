package application;

import domain.User;

import persistence.UserDAO;

import servlet.JsonWebToken;

import shared.Cipher;
import shared.Json;

import java.util.Collection;
import java.util.Optional;

public final class UserService {
  private static UserDAO persistence = new UserDAO();

  public User createUserFromJson(Json json) throws Exception {
    User user = new User();

    user.setUsername(json.get("username").getAsString());

    user.setEmail(json.get("email").getAsString());

    user.setPassword(json.get("password").getAsString());

    persistence.save(user);

    return user;
  }

  public Collection<User> listAllUsers() throws Exception {
    return persistence.fetch();
  }

  public String createAuthenticationToken(Json json) throws Exception {
    String username = json.get("username").getAsString();

    String password = json.get("password").getAsString();

    Optional<User> row = this.persistence.fetch(username);

    if (!row.isPresent()) {
      throw new Exception("Wrong username or password");
    }

    User user = row.get();

    if (user != null && user.getPassword().equals(password)) {
      return JsonWebToken.encode(user);
    }

    throw new Exception("Wrong username or password");
  }
}
