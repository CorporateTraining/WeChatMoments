package com.example.wechatmoments.ui;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.wechatmoments.R;
import com.example.wechatmoments.repository.WeChatRepositoryImpl;

public class WeChatActivity extends AppCompatActivity {
    private WeChatViewModel weChatViewModel;
    private ImageView profileImage, avatar;
    private TextView username;
    private RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat);
        profileImage = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        avatar = findViewById(R.id.avatar);
        requestManager = Glide.with(getApplicationContext());
        obtainViewModel();
        weChatViewModel.findUser();
        weChatUserProfile();
    }

    private void obtainViewModel() {
        WeChatRepository weChatRepository = new WeChatRepositoryImpl();
        weChatViewModel = new ViewModelProvider(this).get(WeChatViewModel.class);
        weChatViewModel.setWeChatRepository(weChatRepository);
    }

    private void weChatUserProfile() {
        weChatViewModel.observerUserModel(this, user -> {
            requestManager.load(user.getProfileImage()).centerCrop().into(profileImage);
            requestManager.load(user.getAvatar()).centerCrop().apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).into(avatar);
            username.setText(user.getNick());
        });
    }

}