package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import shared.Bcrypt;
import shared.Cipher;

public final class Service {
  public static Connection getConnection() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");

      String url = "jdbc:mysql://localhost:3306/sandbox";

      String user = "root";

      String password = "Pi#31415926535";

      return DriverManager.getConnection(url, user, password);
    } catch (ClassNotFoundException | SQLException exception) {
      exception.printStackTrace();
    }

    return null;
  }

  public static Cipher getCipher() {
    return new Bcrypt();
  }
}
