package com.assodepicche.domain;

public final class Username {
  private String value;

  private Username(String value) {
    this.value = value;
  }

  public static Username from(String value) throws IllegalArgumentException {
    if (value == null || value.isEmpty() || value.length() > 32) {
      throw new IllegalArgumentException(
          "A username must contain at least one character and not exceed 32 characters");
    }

    return new Username(value);
  }

  @Override
  public String toString() {
    return this.value;
  }
}
