package com.example.wechatmoments.repository.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

public class GsonUtil {
    public static Gson gson = new Gson();

    public static <T> T stringFromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static <T> List<T> stringFromJsonList(String json, Type type) {
        return gson.fromJson(json, type);
    }

    public static String jsonToString(Object o) {
        return gson.toJson(o);
    }
}
