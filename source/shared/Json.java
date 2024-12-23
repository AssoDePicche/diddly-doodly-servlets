package shared;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.BufferedReader;
import java.io.IOException;

import java.lang.reflect.Type;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;

import java.util.Collection;

class LocalDateAdapter implements JsonSerializer<LocalDate> {
  public JsonElement serialize(LocalDate date, Type type, JsonSerializationContext context) {
    return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
  }
}

public final class Json {
  private static Gson gson =
      new GsonBuilder()
          .setPrettyPrinting()
          .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
          .create();

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
