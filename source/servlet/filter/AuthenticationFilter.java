package servlet.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import jakarta.servlet.annotation.WebFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import servlet.HttpStatus;
import servlet.Jwt;
import servlet.Payload;
import servlet.ServletService;

@WebFilter({"/books", "/logout"})
public final class AuthenticationFilter implements Filter {
  private ServletService service = new ServletService();

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    try {
      String token = service.getAuthorizationHeader((HttpServletRequest) request);

      if (token == null || Jwt.isExpired(token)) {
        throw new IllegalArgumentException("Missing or invalid token");
      }

      chain.doFilter(request, response);
    } catch (Exception exception) {
      exception.printStackTrace();

      service.dispatch(
          (HttpServletResponse) response,
          new Payload<>(HttpStatus.UNAUTHORIZED, exception.getMessage()));
    }
  }
}
