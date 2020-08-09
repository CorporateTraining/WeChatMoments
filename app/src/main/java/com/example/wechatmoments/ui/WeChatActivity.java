package com.example.wechatmoments.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.wechatmoments.R;
import com.example.wechatmoments.repository.WeChatRepositoryImpl;

public class WeChatActivity extends AppCompatActivity {
    private WeChatViewModel weChatViewModel;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static final int INIT_NUMBER = 0;
    public static final int PAGE_COUNT = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        recyclerView = findViewById(R.id.my_recycle_view);
        obtainViewModel();
        weChatViewModel.findUser();
        weChatViewModel.findWeChatMoment(null);
        displayWeChatTweets();
        dropDownRefreshData();
    }

    private void dropDownRefreshData() {
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light,
                android.R.color.holo_purple);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            weChatViewModel.findWeChatMoment(swipeRefreshLayout);
            myAdapter.notifyDataSetChanged();
        });
    }

    private void obtainViewModel() {
        WeChatRepository weChatRepository = new WeChatRepositoryImpl();
        weChatViewModel = new ViewModelProvider(this).get(WeChatViewModel.class);
        weChatViewModel.setWeChatRepository(weChatRepository);
    }

    private void displayWeChatTweets() {
        weChatViewModel.observerWeChatMoment(this, weChatMoments -> {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
            myAdapter = new MyAdapter(weChatViewModel.getWeChatMoments(INIT_NUMBER, PAGE_COUNT), weChatViewModel, weChatViewModel.getUser());
            myAdapter.setHasMoreTweets(weChatMoments.size() > PAGE_COUNT);
            recyclerView.setAdapter(myAdapter);
        });
    }
}