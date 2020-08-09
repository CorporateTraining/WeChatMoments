package com.example.wechatmoments.ui;

import com.example.wechatmoments.repository.entity.User;
import com.example.wechatmoments.repository.entity.WeChatMoment;

import java.util.List;

import io.reactivex.Maybe;

public interface WeChatRepository {
    Maybe<User> findUser();

    Maybe<User> findUserByNetWork();

    Maybe<List<WeChatMoment>> findWeChatMoment();

    Maybe<List<WeChatMoment>> findWeChatMomentByNetWork();
}
