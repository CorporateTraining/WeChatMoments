package com.example.wechatmoments.repository.entity.vo;

import com.example.wechatmoments.repository.entity.User;

public class Comment{
    private String content;
    private User sender;

    public String getContent() {
        return content;
    }

    public User getSender() {
        return sender;
    }
}
