package com.example.wechatmoments.repository;

import com.example.wechatmoments.repository.data.WeChatData;
import com.example.wechatmoments.repository.entity.User;
import com.example.wechatmoments.repository.entity.WeChatMoment;
import com.example.wechatmoments.repository.util.NetworkGetData;
import com.example.wechatmoments.ui.WeChatRepository;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Maybe;

public class WeChatRepositoryImpl implements WeChatRepository {
    private final static String USER_INFO_URL = "https://twc-android-bootcamp.github.io/fake-data/data/weixin/profile.json";
    private final static String WE_CHAT_MOMENT_URL = "https://twc-android-bootcamp.github.io/fake-data/data/weixin/tweets.json";

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

    @Override
    public Maybe<List<WeChatMoment>> findWeChatMoment() {
        return Maybe.create(emitter -> {
            if (WeChatData.weChatMoments != null) {
                emitter.onSuccess(WeChatData.weChatMoments);
            }
            emitter.onComplete();
        });
    }

    @Override
    public Maybe<List<WeChatMoment>> findWeChatMomentByNetWork() {
        return Maybe.create(emitter -> {
            Type type = new TypeToken<List<WeChatMoment>>() {}.getType();
            List<WeChatMoment> weChatMoments = NetworkGetData.getDataListByUrl(WE_CHAT_MOMENT_URL, type);
            emitter.onSuccess(weChatMoments);
            emitter.onComplete();
        });
    }


}
