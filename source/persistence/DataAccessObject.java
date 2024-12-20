package persistence;

import java.sql.SQLException;

import java.util.Collection;
import java.util.Optional;

public interface DataAccessObject<T, ID> {
  void save(T object) throws SQLException;

  void update(T object) throws SQLException;

  void remove(T object) throws SQLException;

  Collection<T> fetch() throws SQLException;

  Optional<T> fetch(ID id) throws SQLException;
}
