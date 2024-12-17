package servlet;

import application.UserService;

import domain.User;

import shared.Json;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.sql.SQLException;

import java.util.Collection;

@SuppressWarnings({"serial"})
@WebServlet("/users")
public final class UserServlet extends HttpServlet {
  private UserService service = new UserService();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      Payload<Collection<User>> payload = new Payload<>(HttpStatus.OK, this.service.listAllUsers());

      response.setStatus(payload.code);

      response.getWriter().println(payload);
    } catch (SQLException exception) {
      Payload<String> payload =
          new Payload<>(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());

      response.setStatus(payload.code);

      response.getWriter().println(payload);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      Json json = Json.from(request.getReader());

      User user = this.service.createUserFromJson(json);

      Payload<User> payload = new Payload<>(HttpStatus.CREATED, user);

      response.setStatus(payload.code);

      response.getWriter().println(payload);
    } catch (SQLException exception) {
      Payload<String> payload =
          new Payload<>(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());

      response.setStatus(payload.code);

      response.getWriter().println(payload);
    } catch (IllegalArgumentException exception) {
      Payload<String> payload = new Payload<>(HttpStatus.BAD_REQUEST, exception.getMessage());

      response.setStatus(payload.code);

      response.getWriter().println(payload);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  @Override
  public void doDelete(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    Payload<String> payload = null;

    try {
      checkAuthorizationToken(request);
    } catch (Exception exception) {
      payload = new Payload<>(HttpStatus.UNAUTHORIZED, exception.getMessage());
    } finally {
      response.setStatus(payload.code);

      response.getWriter().println(payload);
    }
  }

  private void checkAuthorizationToken(HttpServletRequest request) throws Exception {
    String token = getAuthorizationToken(request);

    if (token == null || JsonWebToken.isExpired(token)) {
      throw new Exception("You must log in");
    }
  }

  private String getAuthorizationToken(HttpServletRequest request) {
    String header = request.getHeader("Authorization");

    if (header != null && header.startsWith("Bearer ")) {
      return header.substring(7);
    }

    return null;
  }
}
