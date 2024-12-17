package servlet;

import servlet.HttpStatus;
import servlet.Payload;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@SuppressWarnings({"serial"})
@WebServlet("/logout")
public final class LogoutServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    Payload<String> payload = null;

    String token = request.getHeader("Authorization").substring(7);

    if (token != null) {
      JsonWebToken.addExpiration(token);

      payload = new Payload<>(HttpStatus.OK, "Logout successful");
    } else {
      payload = new Payload<>(HttpStatus.BAD_REQUEST, "You must log in before logging out");
    }

    response.setStatus(payload.code);

    response.getWriter().println(payload);
  }
}
