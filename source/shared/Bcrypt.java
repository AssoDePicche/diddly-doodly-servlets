package com.assodepicche.shared;

import org.mindrot.jbcrypt.BCrypt;

public final class Bcrypt implements Cipher {
  @Override
  public String encrypt(String value) {
    return BCrypt.hashpw(value, BCrypt.gensalt());
  }

  @Override
  public boolean check(String cipher, String value) {
    return BCrypt.checkpw(value, cipher);
  }
}
