package com.example.wechatmoments.ui;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.wechatmoments.repository.data.WeChatData;
import com.example.wechatmoments.repository.entity.User;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeChatViewModel extends ViewModel {
    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    private WeChatRepository weChatRepository;
    private final static String TAG = WeChatViewModel.class.getSimpleName();

    public void setWeChatRepository(WeChatRepository weChatRepository) {
        this.weChatRepository = weChatRepository;
    }

    public void observerUserModel(LifecycleOwner owner, Observer<User> observer) {
        userMutableLiveData.observe(owner, observer);
    }

    public void findUser() {
        Maybe<User> user = weChatRepository.findUser();
        user.subscribeOn(Schedulers.io())
                .switchIfEmpty(weChatRepository.findUserByNetWork()
                        .doOnSuccess(WeChatData::setUser))
                .subscribe(new MaybeObserver<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onSuccess(User user) {
                        userMutableLiveData.postValue(user);
                        Log.d(TAG, "onSuccess: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
}
