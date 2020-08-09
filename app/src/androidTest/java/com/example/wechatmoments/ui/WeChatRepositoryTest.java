package com.example.wechatmoments.ui;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.wechatmoments.repository.WeChatRepositoryImpl;
import com.example.wechatmoments.repository.data.WeChatData;
import com.example.wechatmoments.repository.entity.User;
import com.example.wechatmoments.repository.entity.WeChatMoment;
import com.example.wechatmoments.repository.entity.vo.Comment;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(AndroidJUnit4.class)
public class WeChatRepositoryTest {

    private WeChatRepository weChatRepository = new WeChatRepositoryImpl();

    @Before
    public void setUp() {

    }

    @Test
    public void should_find_correct_user1() {
//        final String CREATE_USER_NAME = "xiaoming";
//         final Integer CREATE_USER_ID = 1;
//         final String CREATE_PASSWORD = "123456";
//        User savedUser = new User(CREATE_USER_ID, CREATE_USER_NAME, Encryptor.md5(CREATE_PASSWORD));
//        appDatabase.userDBDataSource().save(savedUser).subscribeOn(Schedulers.io()).subscribe();
//        userRepository.findByName(CREATE_USER_NAME).test()
//                .assertValue(user -> user.getId() == savedUser.getId());
    }

    @Test
    public void should_find_correct_user() {
        String CREATE_USERNAME = "username";
        String CREATE_NICK = "nick";
        String CREATE_AVATAR = "avatar";
        String CREATE_PROFILE_IMAGE = "profileImage";
        final User createUser = new User(CREATE_USERNAME, CREATE_NICK, CREATE_AVATAR, CREATE_PROFILE_IMAGE);
        WeChatData.setUser(createUser);
        weChatRepository.findUser()
                .test()
                .assertValue(user -> CREATE_NICK.equals(user.getNick()))
                .assertValue(user -> CREATE_AVATAR.equals(user.getAvatar()))
                .assertValue(user -> CREATE_PROFILE_IMAGE.equals(user.getProfileImage()))
                .assertValue(user -> CREATE_USERNAME.equals(user.getUsername()));
    }

    @Test
    public void should_find_correct_user_by_net_work() {
        String USERNAME = "jsmith";
        String NICK = "John Smith";
        String AVATAR = "https://s3.amazonaws.com/uifaces/faces/twitter/mahmoudmetwally/128.jpg";
        String PROFILE_IMAGE = "http://lorempixel.com/480/280/technics/6/";
        weChatRepository.findUserByNetWork()
                .test()
                .assertValue(user -> NICK.equals(user.getNick()))
                .assertValue(user -> AVATAR.equals(user.getAvatar()))
                .assertValue(user -> PROFILE_IMAGE.equals(user.getProfileImage()))
                .assertValue(user -> USERNAME.equals(user.getUsername()));
    }

    @Test
    public void should_find_correct_we_chat_moment() {
        String CREATE_CONTENT = "content";
        List<String> CREATE_IMAGES = new ArrayList<>();
        User CREATE_SENDER = new User();
        List<Comment> CREATE_COMMENTS = new ArrayList<>();
        Integer TYPE = 1;
        List<WeChatMoment> weChatMoments = new ArrayList<>();
        WeChatMoment weChatMoment = new WeChatMoment(CREATE_CONTENT, CREATE_IMAGES, CREATE_SENDER, CREATE_COMMENTS, TYPE);
        weChatMoments.add(weChatMoment);
        WeChatData.setWeChatMoments(weChatMoments);
        weChatRepository.findWeChatMoment()
                .test()
                .assertValue(findWeChatMoments -> findWeChatMoments.size() == 1)
                .assertValue(findWeChatMoments -> CREATE_CONTENT.equals(findWeChatMoments.get(0).getContent()))
                .assertValue(findWeChatMoments -> CREATE_SENDER.equals(findWeChatMoments.get(0).getSender()))
                .assertValue(findWeChatMoments -> CREATE_COMMENTS.equals(findWeChatMoments.get(0).getComments()))
                .assertValue(findWeChatMoments -> CREATE_IMAGES.equals(findWeChatMoments.get(0).getImages()))
                .assertValue(findWeChatMoments -> TYPE.equals(findWeChatMoments.get(0).getType()));
    }

}