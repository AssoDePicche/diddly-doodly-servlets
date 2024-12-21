package servlet;

import domain.User;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Collection;
import java.util.UUID;

import java.util.regex.Pattern;

import persistence.UserDAO;

import shared.Bcrypt;
import shared.Cipher;
import shared.Json;

@SuppressWarnings({"serial"})
@WebServlet("/users")
public final class UserServlet extends HttpServlet {
  private static final String regex =
      "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
  private static final Pattern pattern = Pattern.compile(regex);
  private ServletService service = new ServletService();
  private UserDAO persistence = new UserDAO();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      Collection<User> users = persistence.fetch();

      service.dispatch(response, new Payload<Collection<User>>(HttpStatus.OK, users));
    } catch (Exception exception) {
      service.dispatch(response, exception);
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      Json json = Json.from(request.getReader());

      User user = new User();

      String username = json.get("username").getAsString();

      if (username.isEmpty() || username.length() > 32) {
        throw new IllegalArgumentException("Username must be up to 32 characters length");
      }

      String email = json.get("email").getAsString();

      if (!pattern.matcher(email).matches()) {
        throw new IllegalArgumentException("Invalid email address");
      }

      String password = json.get("password").getAsString();

      if (password.length() < 8) {
        throw new IllegalArgumentException("Password must have at least 8 characters");
      }

      user.setUsername(username);

      user.setEmail(email);

      Cipher cipher = new Bcrypt();

      user.setPassword(cipher.encrypt(password));

      user.setPassword(password);

      if (!persistence.save(user)) {
        throw new Exception(
            "Username '" + username + "' or email '" + email + "' are already registered");
      }

      service.dispatch(response, new Payload<>(HttpStatus.CREATED, user));
    } catch (IllegalArgumentException exception) {
      service.dispatch(response, new Payload<>(HttpStatus.BAD_REQUEST, exception.getMessage()));
    } catch (Exception exception) {
      service.dispatch(response, exception);
    }
  }

  @Override
  public void doDelete(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    try {
      Json json = Json.from(request.getReader());

      User user = new User();

      UUID id = UUID.fromString(json.get("id").getAsString());

      user.setID(id);

      if (!persistence.remove(user)) {
        throw new Exception("User identified by " + id.toString() + " does not exists");
      }

      service.dispatch(
          response,
          new Payload<>(HttpStatus.OK, "User identified by " + id.toString() + " deleted"));
    } catch (Exception exception) {
      service.dispatch(response, new Payload<>(HttpStatus.BAD_REQUEST, exception.getMessage()));
    }
  }
}
