package com.example.wechatmoments.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wechatmoments.R;
import com.example.wechatmoments.repository.WeChatRepositoryImpl;

public class WeChatActivity extends AppCompatActivity {
    private WeChatViewModel weChatViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat);
        obtainViewModel();
        weChatViewModel.findUser();
        weChatViewModel.findWeChatMoment();
        displayWeChatTweets();
    }

    private void obtainViewModel() {
        WeChatRepository weChatRepository = new WeChatRepositoryImpl();
        weChatViewModel = new ViewModelProvider(this).get(WeChatViewModel.class);
        weChatViewModel.setWeChatRepository(weChatRepository);
    }

    private void displayWeChatTweets() {
        weChatViewModel.observerWeChatMoment(this, weChatMoments -> {
            recyclerView = findViewById(R.id.my_recycle_view);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            myAdapter = new MyAdapter(weChatMoments, weChatViewModel, weChatViewModel.getUser());
            recyclerView.setAdapter(myAdapter);
        });
    }

}