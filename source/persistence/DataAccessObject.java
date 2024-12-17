package persistence;

import java.util.Collection;
import java.util.Optional;

public interface DataAccessObject<T, ID> {
  public abstract void save(T object) throws Exception;

  public abstract void update(T object) throws Exception;

  public abstract void remove(T object) throws Exception;

  public abstract Collection<T> fetch() throws Exception;

  public abstract Optional<T> fetch(ID id) throws Exception;
}
