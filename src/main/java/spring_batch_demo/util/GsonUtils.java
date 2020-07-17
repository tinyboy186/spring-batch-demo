package spring_batch_demo.util;

import com.google.gson.Gson;

public class GsonUtils {
  private static final Gson GSON = new Gson();

  public static String toJson(Object obj) {
    return GSON.toJson(obj);
  }

  public static <T> T fromJson(String sJson, Class<T> t) {
    return GSON.fromJson(sJson, t);
  }
}
