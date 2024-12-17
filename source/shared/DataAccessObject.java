package com.assodepicche.shared;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.Collection;
import java.util.Optional;

public abstract class DataAccessObject<T, ID> {
  private Connection connection;

  public DataAccessObject(Connection connection) {
    this.connection = connection;
  }

  public Connection getConnection() {
    return this.connection;
  }

  public abstract void save(T object) throws SQLException;

  public abstract void update(T object) throws SQLException;

  public abstract void remove(T object) throws SQLException;

  public abstract Collection<T> fetch() throws SQLException;

  public abstract Optional<T> fetch(ID id) throws SQLException;
}
