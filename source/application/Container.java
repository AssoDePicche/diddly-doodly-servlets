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
  public static Connection getConnection() throws ClassNotFoundException, SQLException {
    String url = "jdbc:mysql://localhost:3306/sandbox";

    String user = "root";

    String password = "Pi#31415926535";

    Class.forName("com.mysql.cj.jdbc.Driver");

    return DriverManager.getConnection(url, user, password);
  }

  public static Cipher getCipher() {
    return new Bcrypt();
  }
}
