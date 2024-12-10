package com.assodepicche.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import javax.servlet.annotation.WebFilter;

import javax.servlet.http.HttpServletResponse;

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
