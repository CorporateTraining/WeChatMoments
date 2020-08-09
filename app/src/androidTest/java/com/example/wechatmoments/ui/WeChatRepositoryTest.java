package com.example.wechatmoments.ui;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.wechatmoments.repository.WeChatRepositoryImpl;
import com.example.wechatmoments.repository.data.WeChatData;
import com.example.wechatmoments.repository.entity.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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

}