package persistence;

import java.sql.SQLException;

import java.util.Collection;
import java.util.Optional;

public interface DataAccessObject<T, ID> {
  boolean save(T object);

  boolean update(T object);

  boolean remove(T object);

  Collection<T> fetch();

  Optional<T> fetch(ID id);
}
