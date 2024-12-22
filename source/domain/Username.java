package domain;

public final class Username {
  public static String from(String username) throws IllegalArgumentException {
    if (username.isEmpty() || username.length() > 32) {
      throw new IllegalArgumentException("Username must be up to 32 characters length");
    }

    return username;
  }
}
