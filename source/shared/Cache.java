package com.assodepicche.shared;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

import java.util.function.Predicate;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.time.LocalDateTime;

import java.time.temporal.ChronoUnit;

public final class Cache<Key, Value> {
  public static interface ExpiringValue<Value> {
    Value getValue();

    LocalDateTime getCreatedAt();
  }

  private Map<Key, ExpiringValue<Value>> cache = new HashMap<>();

  private long cacheTimeout;

  public Cache(Long cacheTimeout) {
    this.cacheTimeout = cacheTimeout;
  }

  public boolean contains(Key key) {
    return this.cache.containsKey(key);
  }

  public Optional<Value> get(Key key) {
    Predicate<Key> keyIsExpired =
        keyInKeySet -> {
          LocalDateTime createdAt = this.cache.get(keyInKeySet).getCreatedAt();

          LocalDateTime expirationTime = createdAt.plus(this.cacheTimeout, ChronoUnit.MILLIS);

          return LocalDateTime.now().isAfter(expirationTime);
        };

    Stream<Key> stream = this.cache.keySet().parallelStream();

    Set<Key> expiredKeys = stream.filter(keyIsExpired).collect(Collectors.toSet());

    for (Key expiredKey : expiredKeys) {
      this.remove(expiredKey);
    }

    return Optional.ofNullable(this.cache.get(key)).map(ExpiringValue::getValue);
  }

  public void put(Key key, Value value) {
    LocalDateTime now = LocalDateTime.now();

    this.cache.put(
        key,
        new ExpiringValue<Value>() {
          @Override
          public Value getValue() {
            return value;
          }

          @Override
          public LocalDateTime getCreatedAt() {
            return now;
          }
        });
  }

  public void remove(Key key) {
    this.cache.remove(key);
  }

  public void clear() {
    this.cache = new HashMap<>();
  }
}
