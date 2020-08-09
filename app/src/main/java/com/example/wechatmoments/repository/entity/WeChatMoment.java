package com.example.wechatmoments.repository.entity;

import com.example.wechatmoments.repository.entity.vo.Comment;

import java.util.List;

public class WeChatMoment {
    private String content;
    private List<String> images;
    private User sender;
    private List<Comment> comments;
    private Integer type = 1;

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getContent() {
        return content;
    }

    public List<String> getImages() {
        return images;
    }

    public User getSender() {
        return sender;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
