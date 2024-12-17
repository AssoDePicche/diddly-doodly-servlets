package com.assodepicche.servlet;

import com.assodepicche.domain.Email;
import com.assodepicche.domain.Password;
import com.assodepicche.domain.User;
import com.assodepicche.domain.Username;

import com.assodepicche.shared.ConnectionFactory;

import com.assodepicche.infrastructure.UserDAO;

import com.assodepicche.shared.Json;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.Collection;
import java.util.UUID;

@SuppressWarnings({"serial"})
@WebServlet("/users")
public final class UserServlet extends HttpServlet {
  private UserDAO persistence;

  @Override
  public void init() throws ServletException {
    try {
      Connection connection = ConnectionFactory.getConnection();

      this.persistence = new UserDAO(connection);
    } catch (Exception exception) {
      throw new ServletException(exception);
    }
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      Payload<Collection<User>> payload = new Payload<>(HttpStatus.OK, this.persistence.fetch());

      response.setStatus(payload.code);

      response.getWriter().println(payload);
    } catch (SQLException exception) {
      Payload<String> payload =
          new Payload<>(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());

      response.setStatus(payload.code);

      response.getWriter().println(payload);
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      Json json = Json.from(request.getReader());

      Username username = Username.from(json.get("username").getAsString());

      Email email = Email.from(json.get("email").getAsString());

      Password password = Password.from(json.get("password").getAsString());

      User user = new User(username, email, password);

      this.persistence.save(user);

      Payload<User> payload = new Payload<>(HttpStatus.CREATED, user);

      response.setStatus(payload.code);

      response.getWriter().println(payload);
    } catch (SQLException exception) {
      Payload<String> payload =
          new Payload<>(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());

      response.setStatus(payload.code);

      response.getWriter().println(payload);
    } catch (IllegalArgumentException exception) {
      Payload<String> payload = new Payload<>(HttpStatus.BAD_REQUEST, exception.getMessage());

      response.setStatus(payload.code);

      response.getWriter().println(payload);
    }
  }
}
