package com.example.wechatmoments;

import android.app.Application;

import com.example.wechatmoments.repository.WeChatRepositoryImpl;
import com.example.wechatmoments.ui.WeChatRepository;

public class MainApplication extends Application {
    private WeChatRepository weChatRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        weChatRepository = new WeChatRepositoryImpl();
    }

    public WeChatRepository getWeChatRepository() {
        return weChatRepository;
    }
}
