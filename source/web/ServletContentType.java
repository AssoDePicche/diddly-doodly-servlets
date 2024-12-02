package com.assodepicche.web;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import jakarta.servlet.annotation.WebFilter;

@WebFilter(
    filterName = "ServletContentType",
    urlPatterns = {"/*"})
public final class ServletContentType implements Filter {
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    response.setContentType("application/json");

    chain.doFilter(request, response);
  }
}
