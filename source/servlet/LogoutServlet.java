package servlet;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@SuppressWarnings({"serial"})
@WebServlet("/logout")
public final class LogoutServlet extends HttpServlet {
  private ServletService service = new ServletService();

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      String token = service.getAuthorizationHeader(request);

      if (token == null) {
        throw new IllegalArgumentException("Invalid token");
      }

      Jwt.revoke(token);

      service.dispatch(response, new Payload<>(HttpStatus.OK, "Logout successful"));
    } catch (IllegalArgumentException exception) {
      service.dispatch(response, new Payload<>(HttpStatus.BAD_REQUEST, exception.getMessage()));
    } catch (Exception exception) {
      service.dispatch(response, exception);
    }
  }
}
