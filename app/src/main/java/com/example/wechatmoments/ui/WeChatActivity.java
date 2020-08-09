package com.example.wechatmoments.ui;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.wechatmoments.R;
import com.example.wechatmoments.repository.WeChatRepositoryImpl;

public class WeChatActivity extends AppCompatActivity{
    private WeChatViewModel weChatViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyAdapter myAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat);
        obtainViewModel();
        weChatViewModel.findUser();
        weChatViewModel.findWeChatMoment();
        displayWeChatTweets();
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        recyclerView = findViewById(R.id.my_recycle_view);
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light,
                android.R.color.holo_purple);
        swipeRefreshLayout.setOnRefreshListener(() -> new Handler().postDelayed(() -> {
            weChatViewModel.findWeChatMoment();
            myAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }, 1000));
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
            myAdapter = new MyAdapter(weChatMoments, weChatViewModel, weChatViewModel.getUser());
            recyclerView.setAdapter(myAdapter);
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}