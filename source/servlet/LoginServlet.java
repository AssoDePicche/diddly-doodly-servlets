package servlet;

import application.UserService;

import servlet.HttpStatus;
import servlet.Payload;

import shared.Json;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.sql.SQLException;

@SuppressWarnings({"serial"})
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  private UserService service = new UserService();

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Payload<String> payload = null;

    try {
      Json json = Json.from(request.getReader());

      String token = this.service.createAuthenticationToken(json);

      payload = new Payload<>(HttpStatus.OK, token);
    } catch (IllegalArgumentException exception) {
      payload = new Payload<>(HttpStatus.BAD_REQUEST, exception.getMessage());
    } catch (NullPointerException exception) {
      payload = new Payload<>(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    } catch (Exception exception) {
      payload = new Payload<>(HttpStatus.UNAUTHORIZED, exception.getMessage());
    } finally {
      response.setStatus(payload.code);

      response.getWriter().println(payload);
    }
  }
}
