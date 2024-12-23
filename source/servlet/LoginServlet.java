package servlet;

import domain.Password;
import domain.User;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Optional;

import persistence.UserDAO;

import shared.Json;

@SuppressWarnings({"serial"})
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  private ServletService service = new ServletService();
  private UserDAO persistence = new UserDAO();

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      Json json = Json.from(request.getReader());

      String username = json.get("username").getAsString();

      String password = Password.from(json.get("password").getAsString());

      Optional<User> row = persistence.fetch(username);

      if (!row.isPresent()) {
        throw new IllegalArgumentException("Wrong username or password");
      }

      User user = row.get();

      if (!Password.verify(password, user.getPassword())) {
        throw new IllegalArgumentException("Wrong username or password");
      }

      service.dispatch(response, new Payload<>(HttpStatus.OK, Json.from(user)));
    } catch (IllegalArgumentException exception) {
      service.dispatch(response, new Payload<>(HttpStatus.UNAUTHORIZED, exception.getMessage()));
    } catch (Exception exception) {
      service.dispatch(response, exception);
    }
  }
}
