package com.luliang.rxpractice.operators.filter;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by LuLiang on 2018/5/18.
 *
 * 根据 指定事件数量 过滤事件
 * take、takeLast
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */
public class CountFilter {

    private static final String TAG = "CountFilter";

    /**
     * 指定观察者最多能接收到的事件数量
     * 实际上，可理解为：被观察者还是发送了5个事件，只是因为操作符的存在拦截了3个事件，最终观察者接收到的是2个事件
     */
    public void take() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onNext(5);
            }
        })
                // 采用take（）变换操作符
                // 指定了观察者只能接收2个事件
                .take(2)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: 开始连接");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    /**
     * 指定观察者只能接收到被观察者发送的最后几个事件
     */
    public void takeLast() {
        Observable.just(1, 2, 3, 4, 5)
                //指定观察者只能接受被观察者发送的2个事件
                .takeLast(2)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: 开始连接");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
}
