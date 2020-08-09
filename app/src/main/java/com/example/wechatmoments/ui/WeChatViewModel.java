package com.example.wechatmoments.ui;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.wechatmoments.repository.data.WeChatData;
import com.example.wechatmoments.repository.entity.User;
import com.example.wechatmoments.repository.entity.WeChatMoment;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeChatViewModel extends ViewModel {
    private MutableLiveData<List<WeChatMoment>> weChatMomentsMutableLiveData = new MutableLiveData<>();
    private WeChatRepository weChatRepository;
    private User user = new User();
    private final static String TAG = WeChatViewModel.class.getSimpleName();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void setWeChatRepository(WeChatRepository weChatRepository) {
        this.weChatRepository = weChatRepository;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void observerWeChatMoment(LifecycleOwner owner, Observer<List<WeChatMoment>> observer) {
        weChatMomentsMutableLiveData.observe(owner, observer);
    }

    public void findUser() {
        Maybe<User> user = weChatRepository.findUser();
        user.subscribeOn(Schedulers.io())
                .switchIfEmpty(weChatRepository.findUserByNetWork()
                        .doOnSuccess(WeChatData::setUser))
                .subscribe(new MaybeObserver<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onSuccess(User user) {
                        setUser(user);
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

    public void findWeChatMoment() {
        Maybe<List<WeChatMoment>> weChatMoment = weChatRepository.findWeChatMoment();
        weChatMoment.subscribeOn(Schedulers.io())
                .switchIfEmpty(weChatRepository.findWeChatMomentByNetWork()
                        .doOnSuccess(WeChatData::setWeChatMoments))
                .subscribe(new MaybeObserver<List<WeChatMoment>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        Log.d(TAG, "onSubscribe:");
                    }

                    @Override
                    public void onSuccess(List<WeChatMoment> weChatMoments) {
                        List<WeChatMoment> weChatMomentsFilter = weChatMoments.stream().filter(weChatMoment -> weChatMoment.getSender() != null).collect(Collectors.toList());
                        weChatMomentsMutableLiveData.postValue(weChatMomentsFilter);
                        Log.d(TAG, "onSuccess:");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError:", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete:");
                    }
                });

    }

    @Override
    protected void onCleared() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        super.onCleared();
    }
}
