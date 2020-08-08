package com.example.wechatmoments.repository.data;

import com.example.wechatmoments.repository.entity.User;

public class WeChatData {
    public static User user;

    public static void setUser(User user) {
        WeChatData.user = user;
    }
}
