package com.assodepicche.shared;

public interface Cipher {
  String encrypt(String value);

  boolean check(String cipher, String value);
}
