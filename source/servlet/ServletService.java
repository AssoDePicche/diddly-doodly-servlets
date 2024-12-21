package servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public final class ServletService {
  private HttpServletRequest request;
  private HttpServletResponse response;

  public boolean isAuthorized(HttpServletRequest request) {
    String header = request.getHeader("Authorization");

    if (header == null || !header.startsWith("Bearer ")) {
      return false;
    }

    String token = header.substring(7).trim();

    return !token.isEmpty() && !JsonWebToken.isExpired(token);
  }

  public void dispatch(Payload payload) throws IOException {
    this.response.setStatus(payload.code);

    this.response.getWriter().println(payload.toString());

    this.response.getWriter().flush();
  }

  public void dispatch(HttpServletResponse response, Payload payload) throws IOException {
    response.setStatus(payload.code);

    response.getWriter().println(payload.toString());

    response.getWriter().flush();
  }

  public void dispatch(HttpServletResponse response, Exception exception) throws IOException {
    dispatch(response, new Payload<>(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));

    exception.printStackTrace();
  }
}
