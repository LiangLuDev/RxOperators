package com.luliang.rxpractice.operators.utility;

import android.annotation.SuppressLint;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by LuLiang on 2018/5/17.
 * 常用功能性操作符
 * 连接被观察者&观察者 subscribe
 * 线程调度 subscribeOn、observeOn
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */
public class AlwaysUtility {

    private static final String TAG = "AlwaysUtility";

    /**
     * 观察者 被观察者连接
     */
    public void subscribe() {
        Observable<Integer> observable = Observable.just(1, 2, 3);
        observable.subscribe(new Observer<Integer>() {
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
     * subscribeOn 指定被观察者 生产事件的线程 (例：网络请求等耗时操作需要指定子线程)
     * observeOn  多次指定观察者 响应事件的线程
     * <p>
     * 线程类型：
     * 1、AndroidSchedulers.mainThread  主线程                操作UI
     * 2、Schedulers.newThread          常规新线程            耗时操作
     * 3、Schedulers.io                 io操作线程            网络请求/读写文件
     * 4、Schedulers.computation         CPU计算操作线程       大量计算操作
     */
    @SuppressLint("CheckResult")
    public void switchSubscribes() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                Thread.sleep(1000);
                emitter.onNext(2);

                emitter.onComplete();
            }
        })
                //指定发送事件的线程（多次指定以最后一次为准）
                .subscribeOn(Schedulers.io())
                //指定响应事件的线程（可多次指定线程）
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: "+integer);
                    }
                });
    }

}
