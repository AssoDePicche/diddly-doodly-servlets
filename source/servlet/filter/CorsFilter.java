package com.assodepicche.servlet.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import jakarta.servlet.annotation.WebFilter;

import jakarta.servlet.http.HttpServletResponse;

@WebFilter(
    filterName = "CorsFilter",
    urlPatterns = {"/*"})
public final class CorsFilter implements Filter {
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    ((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");

    ((HttpServletResponse) response)
        .addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST");

    ((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers", "*");

    chain.doFilter(request, response);
  }
}
