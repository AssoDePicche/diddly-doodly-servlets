package servlet;

import domain.User;

import shared.Json;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Collection;
import java.util.UUID;

import persistence.UserDAO;

import shared.Bcrypt;

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

      User user = new User();

      user.setUsername(json.get("username").getAsString());

      user.setEmail(json.get("email").getAsString());

      Bcrypt bcrypt = new Bcrypt();

      String password = bcrypt.encrypt(json.get("password").getAsString());

      user.setPassword(password);

      if (!persistence.save(user)) {
        throw new Exception("Err: " + user.toString());
      }

      service.dispatch(response, new Payload<User>(HttpStatus.CREATED, user));
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
        throw new Exception("Cannot delete user");
      }

      service.dispatch(response, new Payload<>(HttpStatus.OK, "User deleted"));
    } catch (Exception exception) {
      service.dispatch(response, exception);
    }
  }
}
