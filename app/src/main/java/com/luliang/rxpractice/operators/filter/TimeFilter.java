package com.luliang.rxpractice.operators.filter;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by LuLiang on 2018/5/18.
 * <p>
 * 根据 指定时间 过滤事件
 * <p>
 * throttleFirst、throttleLast、throttleWithTimeout、sample
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */
public class TimeFilter {

    private static final String TAG = "TimeFilter";

    /**
     * 在某段时间内，只发送该段时间内第1次事件
     * 例   1段时间内连续点击按钮，但只执行第1次的点击操作
     */
    public void throttleFirst() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                // 隔段时间发送事件
                emitter.onNext(1);
                Thread.sleep(500);
                emitter.onNext(2);
                Thread.sleep(500);
                emitter.onNext(3);
            }
        })
                .throttleFirst(1, TimeUnit.SECONDS)
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
     * 在某段时间内，只发送该段时间内最后1次事件
     * sample功能同此
     */
    public void throttleLast() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                // 隔段时间发送事件
                emitter.onNext(1);
                Thread.sleep(500);
                emitter.onNext(2);
                Thread.sleep(500);
                emitter.onNext(3);
            }
        })
                .throttleLast(1, TimeUnit.SECONDS)
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
     * 发送数据事件时，若2次发送事件的间隔＜指定时间，就会丢弃前一次的数据，直到指定时间内都没有新数据发射时才会发送后一次的数据
     */
    public void throttleWithTimeout(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                // 隔段事件发送时间
                e.onNext(1);
                Thread.sleep(500);
                e.onNext(2); // 1和2之间的间隔小于指定时间1s，所以前1次数据（1）会被抛弃，2会被保留
                Thread.sleep(1500);  // 因为2和3之间的间隔大于指定时间1s，所以之前被保留的2事件将发出
                e.onNext(3);
                Thread.sleep(1500);  // 因为3和4之间的间隔大于指定时间1s，所以3事件将发出
                e.onNext(4);
                Thread.sleep(500); // 因为4和5之间的间隔小于指定时间1s，所以前1次数据（4）会被抛弃，5会被保留
                e.onNext(5);
                Thread.sleep(500); // 因为5和6之间的间隔小于指定时间1s，所以前1次数据（5）会被抛弃，6会被保留
                e.onNext(6);
                Thread.sleep(1500); // 因为6和Complete实践之间的间隔大于指定时间1s，所以之前被保留的6事件将发出
                e.onComplete();
            }
        })
                .throttleWithTimeout(1,TimeUnit.SECONDS)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: 开始连接");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: "+integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }





}
