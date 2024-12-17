package com.assodepicche.domain;

import com.assodepicche.shared.Bcrypt;
import com.assodepicche.shared.Cipher;

public final class Password {
  private static Cipher cipher = new Bcrypt();

  private String value;

  private Password(String value) {
    this.value = cipher.encrypt(value);
  }

  public static Password from(String value) throws IllegalArgumentException {
    if (value == null || value.isEmpty() || value.length() < 8 || value.length() > 32) {
      throw new IllegalArgumentException("A password  must contain at least 8 characters");
    }

    return new Password(value);
  }

  @Override
  public String toString() {
    return this.value;
  }

  public boolean equals(String value) {
    return cipher.check(this.value, value);
  }
}
