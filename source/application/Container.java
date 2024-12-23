package application;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public final class Container {
  private static final HikariDataSource dataSource;

  static {
    HikariConfig configuration = new HikariConfig();

    configuration.setJdbcUrl("jdbc:mysql://localhost:3306/sandbox");

    configuration.setUsername("root");

    configuration.setPassword("Pi#31415926535");

    configuration.setDriverClassName("com.mysql.cj.jdbc.Driver");

    configuration.setMaximumPoolSize(10);

    configuration.setMinimumIdle(2);

    configuration.setIdleTimeout(30000);

    configuration.setConnectionTimeout(30000);

    dataSource = new HikariDataSource(configuration);
  }

  public static Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }
}
