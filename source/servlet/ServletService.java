package servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public final class ServletService {
  public boolean isAuthorized(HttpServletRequest request) {
    String token = getAuthorizationHeader(request);

    return !token.isEmpty() && !Jwt.isExpired(token);
  }

  public String getAuthorizationHeader(HttpServletRequest request) {
    String header = request.getHeader("Authorization");

    if (header == null) {
      return null;
    }

    return header.substring(7);
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
