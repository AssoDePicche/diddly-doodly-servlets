package shared;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class Bcrypt implements Cipher {
  private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  @Override
  public String encrypt(String value) {
    return encoder.encode(value);
  }

  @Override
  public boolean check(String value, String cipher) {
    return encoder.matches(value, cipher);
  }
}
