package servlet;

import domain.User;

import shared.Json;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Collection;

import persistence.UserDAO;

@SuppressWarnings({"serial"})
@WebServlet("/users")
public final class UserServlet extends HttpServlet {
  private ServletService service = new ServletService();
  private UserDAO persistence = new UserDAO();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      Collection<User> users = this.persistence.fetch();

      service.dispatch(response, new Payload<Collection<User>>(HttpStatus.OK, users));
    } catch (Exception exception) {
      service.dispatch(
          response, new Payload<String>(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));

      exception.printStackTrace();
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      Json json = Json.from(request.getReader());

      User user = new User();

      user.setUsername(json.get("username").getAsString());

      user.setEmail(json.get("email").getAsString());

      user.setPassword(json.get("password").getAsString());

      if (!this.persistence.save(user)) {
        throw new Exception("Cannot create user this time, try again");
      }

      service.dispatch(response, new Payload<User>(HttpStatus.CREATED, user));
    } catch (Exception exception) {
      service.dispatch(
          response, new Payload<String>(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));

      exception.printStackTrace();
    }
  }

  @Override
  public void doDelete(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    Payload<String> payload = new Payload<>(HttpStatus.UNAUTHORIZED, "");

    service.dispatch(response, payload);
  }
}
