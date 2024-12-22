package domain;

import shared.Bcrypt;
import shared.Cipher;

public final class Password {
  public static Cipher cipher = new Bcrypt();

  public static String from(String password) throws IllegalArgumentException {
    if (password.length() < 8) {
      throw new IllegalArgumentException("Password must have at least 8 characters");
    }

    return password;
  }

  public static String hash(String password) {
    return cipher.encrypt(password);
  }

  public static boolean verify(String password, String hash) {
    return cipher.check(password, hash);
  }
}
