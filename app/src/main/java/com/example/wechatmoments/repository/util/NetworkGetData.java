package com.example.wechatmoments.repository.util;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class NetworkGetData {
    public static <T> T getDataByUrl(String url, Class<T> tClass) {
        T t = null;
        try {
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
            t = GsonUtil.stringFromJson(responseBody, tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }
}
