package com.example.wechatmoments.ui;

import com.example.wechatmoments.repository.entity.User;

import io.reactivex.Maybe;

public interface WeChatRepository {
    Maybe<User> findUser();

    Maybe<User> findUserByNetWork();
}
