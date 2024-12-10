package com.assodepicche.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import javax.servlet.annotation.WebFilter;

@WebFilter(
    filterName = "ContentTypeFilter",
    urlPatterns = {"/*"})
public final class ContentTypeFilter implements Filter {
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    response.setContentType("application/json");

    chain.doFilter(request, response);
  }
}
