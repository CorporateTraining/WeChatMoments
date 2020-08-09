package com.example.wechatmoments.repository.data;

import com.example.wechatmoments.repository.entity.User;
import com.example.wechatmoments.repository.entity.WeChatMoment;

import java.util.List;

public class WeChatData {
    public static User user;
    public static List<WeChatMoment> weChatMoments;

    public static void setUser(User user) {
        WeChatData.user = user;
    }

    public static void setWeChatMoments(List<WeChatMoment> weChatMoments) {
        WeChatData.weChatMoments = weChatMoments;
    }
}
