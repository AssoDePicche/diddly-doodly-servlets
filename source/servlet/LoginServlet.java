package servlet;

import application.Container;

import domain.User;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.sql.SQLException;

import java.util.Optional;

import persistence.UserDAO;

import shared.Json;

@SuppressWarnings({"serial"})
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  private UserDAO persistence() {
    return Container.getDAO(UserDAO.class);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Payload payload = null;

    try {
      Json json = Json.from(request.getReader());

      String username = json.get("username").getAsString();

      String password = json.get("password").getAsString();

      Optional<User> row = persistence().fetch(username);

      if (!row.isPresent()) {
        throw new Exception("Wrong username or password");
      }

      User user = row.get();

      if (user == null || !Container.getCipher().check(user.getPassword(), password)) {
        throw new Exception("Wrong username or password");
      }

      payload = new Payload(HttpStatus.OK, JsonWebToken.encode(user));
    } catch (IllegalArgumentException exception) {
      payload = new Payload(HttpStatus.BAD_REQUEST, exception.getMessage());
    } catch (Exception exception) {
      payload = new Payload(HttpStatus.UNAUTHORIZED, exception.getMessage());
    } finally {
      new ServletService(request, response).dispatch(payload);
    }
  }
}
