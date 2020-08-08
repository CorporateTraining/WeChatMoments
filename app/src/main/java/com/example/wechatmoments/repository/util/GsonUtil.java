package com.example.wechatmoments.repository.util;

import com.google.gson.Gson;

public class GsonUtil {
    public static Gson gson = new Gson();

    public static <T> T stringFromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static String jsonToString(Object o) {
        return gson.toJson(o);
    }
}
