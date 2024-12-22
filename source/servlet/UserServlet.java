package servlet;

import domain.Password;
import domain.User;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Collection;
import java.util.UUID;

import persistence.UserDAO;

import shared.Json;

@SuppressWarnings({"serial"})
@WebServlet("/users")
public final class UserServlet extends HttpServlet {
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

      String username = json.get("username").getAsString();

      String email = json.get("email").getAsString();

      String password = json.get("password").getAsString();

      User user = new User();

      user.setUsername(username);

      user.setEmail(email);

      user.setPassword(Password.hash(password));

      if (!persistence.save(user)) {
        throw new IllegalArgumentException(
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
