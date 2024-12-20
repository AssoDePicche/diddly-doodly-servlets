package servlet;

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
    ServletService service = new ServletService(request, response);

    Payload payload = null;

    String token = "";

    if (service.isAuthorized()) {
      payload = new Payload(HttpStatus.OK, "Logout successful");
    } else {
      payload = new Payload(HttpStatus.BAD_REQUEST, "You must log in before logging out");
    }

    service.dispatch(payload);
  }
}
