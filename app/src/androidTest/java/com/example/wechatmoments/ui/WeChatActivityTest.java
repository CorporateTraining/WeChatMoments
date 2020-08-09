package com.example.wechatmoments.ui;

import android.os.SystemClock;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.wechatmoments.MainApplication;
import com.example.wechatmoments.R;
import com.example.wechatmoments.repository.entity.User;
import com.example.wechatmoments.repository.entity.WeChatMoment;
import com.example.wechatmoments.repository.entity.vo.Comment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.internal.operators.maybe.MaybeCreate;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.when;


@RunWith(AndroidJUnit4.class)
public class WeChatActivityTest {
    private final static String CREATE_USERNAME = "username";
    private final static String CREATE_NICK = "nick";
    private final static String CREATE_AVATAR = "avatar";
    private final static String CREATE_PROFILE_IMAGE = "profileImage";
    private final static String CREATE_CONTENT = "content";
    private static final List<String> CREATE_IMAGES = null;
    private static final List<Comment> CREATE_COMMENTS = null;
    private static final Integer TYPE = 1;


    @Rule
    public ActivityTestRule<WeChatActivity> mActivityRule = new ActivityTestRule<>(WeChatActivity.class, false, false);

    @Before
    public void before() {
        MainApplication applicationContext = (MainApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        WeChatRepository weChatRepository = applicationContext.getWeChatRepository();
        User createUser = new User(CREATE_USERNAME, CREATE_NICK, CREATE_AVATAR, CREATE_PROFILE_IMAGE);
        when(weChatRepository.findUser()).thenReturn(new MaybeCreate<>(emitter -> emitter.onSuccess(createUser)));
        when(weChatRepository.findUserByNetWork()).thenReturn(new MaybeCreate<>(emitter -> emitter.onSuccess(createUser)));
        List<WeChatMoment> weChatMoments = new ArrayList<>();
        WeChatMoment weChatMoment = new WeChatMoment(CREATE_CONTENT, CREATE_IMAGES, createUser, CREATE_COMMENTS, TYPE);
        weChatMoments.add(weChatMoment);
        when(weChatRepository.findWeChatMoment()).thenReturn(new MaybeCreate<>(emitter -> emitter.onSuccess(weChatMoments)));
        when(weChatRepository.findWeChatMomentByNetWork()).thenReturn(new MaybeCreate<>(emitter -> emitter.onSuccess(weChatMoments)));
        mActivityRule.launchActivity(null);
    }

    @Test
    public void should_return_header_and_one_moment() {
        SystemClock.sleep(1000);
        onView(withId(R.id.my_recycle_view))
                .perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.my_recycle_view))
                .perform(actionOnItemAtPosition(1, click()));
    }
}