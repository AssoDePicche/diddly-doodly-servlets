package domain;

import java.util.regex.Pattern;

public final class Email {
  private static final String regex =
      "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
  private static final Pattern pattern = Pattern.compile(regex);

  public static String from(String email) throws IllegalArgumentException {
    if (!pattern.matcher(email).matches()) {
      throw new IllegalArgumentException("Invalid email address");
    }

    return email;
  }
}
