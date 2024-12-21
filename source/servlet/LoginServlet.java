package servlet;

import domain.User;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Optional;

import persistence.UserDAO;

import shared.Bcrypt;
import shared.Cipher;
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

      if (username.isEmpty() || username.length() > 32) {
        throw new IllegalArgumentException("Username must be up to 32 characters length");
      }

      String password = json.get("password").getAsString();

      if (password.length() < 8) {
        throw new IllegalArgumentException("Password must have at least 8 characters");
      }

      Optional<User> row = persistence.fetch(username);

      if (!row.isPresent()) {
        throw new IllegalArgumentException("Wrong username or password");
      }

      User user = row.get();

      Cipher cipher = new Bcrypt();

      if (cipher.check(password, user.getPassword())) {
        throw new IllegalArgumentException("Wrong username or password");
      }

      service.dispatch(response, new Payload<>(HttpStatus.OK, JsonWebToken.encode(user)));
    } catch (IllegalArgumentException exception) {
      service.dispatch(response, new Payload<>(HttpStatus.BAD_REQUEST, exception.getMessage()));
    } catch (Exception exception) {
      service.dispatch(response, exception);
    }
  }
}
