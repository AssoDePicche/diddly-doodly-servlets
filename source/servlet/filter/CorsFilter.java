package servlet.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import jakarta.servlet.annotation.WebFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import servlet.HttpStatus;
import servlet.Payload;
import servlet.ServletService;

@WebFilter("/*")
public final class CorsFilter implements Filter {
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    ((HttpServletResponse) response)
        .setHeader("Access-Control-Allow-Origin", "http://localhost:8081");

    ((HttpServletResponse) response).setHeader("Access-Control-Allow-Methods", "*");

    ((HttpServletResponse) response).setHeader("Access-Control-Allow-Headers", "*");

    ((HttpServletResponse) response).setHeader("Access-Control-Allow-Credentials", "true");

    if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) request).getMethod())) {
      new ServletService()
          .dispatch((HttpServletResponse) response, new Payload<>(HttpStatus.OK, ""));

      return;
    }

    chain.doFilter(request, response);
  }
}
