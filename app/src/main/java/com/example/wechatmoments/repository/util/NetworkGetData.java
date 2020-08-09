package com.example.wechatmoments.repository.util;

import com.example.wechatmoments.repository.entity.WeChatMoment;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class NetworkGetData {
    public static <T> T getDataByUrl(String url, Class<T> tClass) {
        T t = null;
        try {
            String responseBody = getBodyFromHttpClient(url);
            t = GsonUtil.stringFromJson(responseBody, tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    @NotNull
    private static String getBodyFromHttpClient(String url) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url(url)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        String responseBody = "";
        ResponseBody body = response.body();
        if (body != null) {
            responseBody = body.string();
        }
        return responseBody;
    }

    public static <T> List<T> getDataListByUrl(String url, Type type) {
        List<T> t = null;
        try {
            String responseBody = getBodyFromHttpClient(url);
            t = GsonUtil.stringFromJsonList(responseBody,  type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }
}
