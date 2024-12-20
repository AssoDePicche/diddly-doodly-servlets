package shared;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;

public final class Json {
  private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

  private JsonObject jsonObject;

  private Json(JsonObject jsonObject) {
    this.jsonObject = jsonObject;
  }

  public static Json from(BufferedReader reader) {
    return new Json(JsonParser.parseReader(reader).getAsJsonObject());
  }

  public JsonElement get(String key) {
    return this.jsonObject.get(key);
  }

  public static <T> T from(String json, Class<T> classOfT) {
    return gson.fromJson(json, classOfT);
  }

  public static String from(Object object) {
    return gson.toJson(object);
  }
}
