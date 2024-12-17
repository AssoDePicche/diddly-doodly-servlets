package servlet;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@SuppressWarnings({"serial"})
@WebServlet("/books")
public final class BookServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) {}

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) {}

  @Override
  public void doPut(HttpServletRequest request, HttpServletResponse response) {}

  @Override
  public void doDelete(HttpServletRequest request, HttpServletResponse response) {}
}
