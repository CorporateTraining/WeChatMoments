package com.example.wechatmoments.repository;

import com.example.wechatmoments.repository.data.WeChatData;
import com.example.wechatmoments.repository.entity.User;
import com.example.wechatmoments.repository.util.NetworkGetData;
import com.example.wechatmoments.ui.WeChatRepository;

import io.reactivex.Maybe;

public class WeChatRepositoryImpl implements WeChatRepository {
    private final static String USER_INFO_URL = "https://twc-android-bootcamp.github.io/fake-data/data/weixin/profile.json";

    @Override
    public Maybe<User> findUser() {
        return Maybe.create(emitter -> {
            if (WeChatData.user != null) {
                emitter.onSuccess(WeChatData.user);
            }
            emitter.onComplete();
        });
    }

    @Override
    public Maybe<User> findUserByNetWork() {
        return Maybe.create(emitter -> {
            User user = NetworkGetData.getDataByUrl(USER_INFO_URL, User.class);
            emitter.onSuccess(user);
            emitter.onComplete();
        });
    }
}
