package com.example.wechatmoments.repository.entity;

import com.google.gson.annotations.SerializedName;

public class User {
    private String username;
    private String nick;
    private String avatar;
    @SerializedName("profile-image")
    private String profileImage;

    public User() {
    }

    public User(String username, String nick, String avatar, String profileImage) {
        this.username = username;
        this.nick = nick;
        this.avatar = avatar;
        this.profileImage = profileImage;
    }

    public String getNick() {
        return nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getUsername() {
        return username;
    }
}
