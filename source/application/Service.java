package application;

import java.io.InputStream;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;

public final class Service {
  private static Properties properties;

  private static Connection connection;

  public static String getProperty(String key) {
    if (properties != null) {
      return properties.getProperty(key);
    }

    Service instance = new Service();

    InputStream stream =
        instance.getClass().getClassLoader().getResourceAsStream("application.properties");

    try {
      if (stream == null) {
        throw new IOException("Property file not found");
      }

      properties.load(stream);

      stream.close();
    } catch (IOException exception) {
      exception.printStackTrace();
    }

    return properties.getProperty(key);
  }

  public static Connection getConnection() {
    if (connection != null) {
      return connection;
    }

    try {
      Class.forName(getProperty("database.driver"));

      connection =
          DriverManager.getConnection(
              getProperty("database.url"),
              getProperty("database.user"),
              getProperty("database.password"));
    } catch (ClassNotFoundException | SQLException exception) {
      exception.printStackTrace();
    }

    return connection;
  }
}
