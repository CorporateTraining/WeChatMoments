package com.example.wechatmoments;

import android.app.Application;

import com.example.wechatmoments.repository.WeChatRepositoryImpl;
import com.example.wechatmoments.ui.WeChatRepository;

import static org.mockito.Mockito.mock;

public class MainApplication extends Application {
    private WeChatRepository weChatRepository;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public WeChatRepository getWeChatRepository() {
        if (weChatRepository == null) {
            weChatRepository = mock(WeChatRepositoryImpl.class);
        }
        return weChatRepository;
    }
}
