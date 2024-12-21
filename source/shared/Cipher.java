package shared;

public interface Cipher {
  String encrypt(String value);

  boolean check(String value, String cipher);
}
