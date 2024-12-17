package com.assodepicche.domain;

import java.util.regex.Pattern;

public final class Email {
  private static String regex =
      "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

  private static Pattern pattern = Pattern.compile(regex);

  private String value;

  private Email(String value) {
    this.value = value;
  }

  public static Email from(String value) throws IllegalArgumentException {
    if (value == null
        || value.isEmpty()
        || value.length() > 255
        || !pattern.matcher(value).matches()) {
      throw new IllegalArgumentException(
          "A email address consists of an email prefix and an email domain");
    }

    return new Email(value);
  }

  @Override
  public String toString() {
    return this.value;
  }
}
