package servlet;

import application.Container;

import domain.User;

import shared.Json;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.sql.SQLException;

import persistence.UserDAO;

@SuppressWarnings({"serial"})
@WebServlet("/users")
public final class UserServlet extends HttpServlet {
  private ServletService dispatcher = new ServletService();

  private UserDAO persistence() {
    return Container.getDAO(UserDAO.class);
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      Payload payload = new Payload(HttpStatus.OK, persistence().fetch());

      dispatcher.dispatch(response, payload);
    } catch (Exception exception) {
      dispatcher.dispatch(
          response, new Payload(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
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

      persistence().save(user);

      dispatcher.dispatch(response, new Payload(HttpStatus.CREATED, user));
    } catch (SQLException exception) {
      dispatcher.dispatch(
          response, new Payload(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
    } catch (IllegalArgumentException exception) {
      dispatcher.dispatch(response, new Payload(HttpStatus.BAD_REQUEST, exception.getMessage()));
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  @Override
  public void doDelete(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    Payload payload = new Payload(HttpStatus.UNAUTHORIZED, "");

    dispatcher.dispatch(response, payload);
  }
}
