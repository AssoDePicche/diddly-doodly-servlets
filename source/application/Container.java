package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import shared.Bcrypt;
import shared.Cipher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import shared.Bcrypt;
import shared.Cipher;

public final class Container {
  public static Connection getConnection() {
    try {
      String url = "jdbc:mysql://localhost:3306/sandbox";
      String user = "root";
      String password = "Pi#31415926535";

      Class.forName("com.mysql.cj.jdbc.Driver");

      return DriverManager.getConnection(url, user, password);
    } catch (ClassNotFoundException | SQLException exception) {
      throw new RuntimeException("Failed to establish database connection");
    }
  }

  public static Cipher getCipher() {
    return new Bcrypt();
  }

  public static <T> T getDAO(Class<T> className) {
    try {
      return className.getDeclaredConstructor(Connection.class).newInstance(getConnection());
    } catch (Exception exception) {
      // throw new RuntimeException("Failed to create DAO instance for " + className.getName());
      throw new RuntimeException(exception.getMessage());
    }
  }
}
