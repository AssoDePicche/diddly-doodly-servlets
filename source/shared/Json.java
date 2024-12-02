package com.assodepicche.shared;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;

public final class Json {
  private static Gson gson;

  static {
    gson = new GsonBuilder().setPrettyPrinting().create();
  }

  public static <T> T from(String json, Class<T> classOfT) {
    return new Gson().fromJson(json, classOfT);
  }

  public static String from(Object object) {
    return new Gson().toJson(object);
  }
}
