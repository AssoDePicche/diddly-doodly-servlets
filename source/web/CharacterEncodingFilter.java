package com.assodepicche.web;

import java.io.IOException;

import java.nio.charset.StandardCharsets;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import jakarta.servlet.annotation.WebFilter;

@WebFilter(
    filterName = "CharacterEncodingFilter",
    urlPatterns = {"/*"})
public final class CharacterEncodingFilter implements Filter {
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());

    chain.doFilter(request, response);
  }
}
