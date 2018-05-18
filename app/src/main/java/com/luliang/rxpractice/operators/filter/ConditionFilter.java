package com.luliang.rxpractice.operators.filter;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by LuLiang on 2018/5/18.
 * 根据指定条件过滤事件
 * filter、ofType、skip、skipLast、distinct、distinctUntilChanged
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */
public class ConditionFilter {

    private static final String TAG = "ConditionFilter";

    /**
     * 过滤 特定条件的事件
     */
    public void filter() {
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
                //filter过滤操作符
                .filter(new Predicate<Integer>() {
                    // 根据test()的返回值 对被观察者发送的事件进行过滤 & 筛选
                    // a. 返回true，则继续发送
                    // b. 返回false，则不发送（即过滤）
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        Log.d(TAG, "filter: " + integer);
                        return integer > 3;
                    }
                })
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
     * 过滤 特定数据类型的数据
     */
    @SuppressLint("CheckResult")
    public void ofType() {
        Observable.just(1, "LuLiangDev", 2)
                .ofType(Integer.class)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "ofType: " + integer);
                    }
                });
    }

    /**
     * 跳过某个事件
     */
    @SuppressLint("CheckResult")
    public void skipAndSkipLast() {
        //根据顺序跳过数据项
        Observable.just(1, 2, 3, 4)
                // 跳过正序的前1项
                .skip(1)
                // 跳过正序的后1项
                .skipLast(1)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "获取到的整型事件元素是: " + integer);
                    }
                });

        //根据时间跳过数据项
        //发送事件特点：发送数据0-5，每隔1s发送一次，每次递增1；第1次发送延迟0s
        Observable.intervalRange(0, 5, 0, 1, TimeUnit.SECONDS)
                // 跳过第1s发送的数据
                .skip(1, TimeUnit.SECONDS)
                // 跳过最后1s发送的数据
                .skipLast(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d(TAG, "获取到的整型事件元素是: " + aLong);
                    }
                });
    }

    /**
     * 过滤事件序列中重复的事件
     */
    @SuppressLint("CheckResult")
    public void distinct() {
        Observable.just(1, 2, 3, 3, 4, 5, 5)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: " + integer);
                    }
                });
    }

    /**
     * 过滤事件序列中 连续重复的事件
     * 下面序列中，连续重复的事件 = 3
     */
    @SuppressLint("CheckResult")
    public void distinctUntilChanged() {
        Observable.just(1, 2, 3, 1, 2, 3, 3, 5)
                .distinctUntilChanged()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: " + integer);
                    }
                });
    }


}
