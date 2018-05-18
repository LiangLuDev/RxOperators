package com.luliang.rxpractice.operators.conditional;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by LuLiang on 2018/5/18.
 *
 * 条件/布尔操作符
 * 通过设置函数，判断被观察者（Observable）发送的事件是否符合条件
 *
 * all
 * takeWhile、skipWhile、takeUntil、skipUntil
 * SequenceEqual、contains
 * isEmpty、amb
 * defaultIfEmpty
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */
public class ConditionalAndBoolean {

    private static final String TAG = "ConditionalAndBoolean";

    /**
     * 判断发送的每项数据是否都满足 设置的函数条件
     * 若满足，返回 true；否则，返回 false
     */
    @SuppressLint("CheckResult")
    public void all(){
        Observable.just(1,2,3,4,5)
                .all(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return (integer<=4);
                    }
                })
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.d(TAG, "accept: "+aBoolean);
                    }
                });
    }

    /**
     * 判断发送的每项数据是否满足 设置函数条件
     * 若发送的数据满足该条件，则发送该项数据；否则不发送
     */
    public void takeWhile(){
        //每1s发送1个数据 = 从0开始，递增1，即0、1、2、3
        Observable.interval(1, TimeUnit.SECONDS)
                //  通过takeWhile传入一个判断条件
                .takeWhile(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        return (aLong<3);
                        // 当发送的数据满足<3时，才发送Observable的数据
                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: 开始连接");
            }

            @Override
            public void onNext(Long aLong) {
                Log.d(TAG, "onNext: "+aLong);
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

    /**
     * 判断发送的每项数据是否满足 设置函数条件
     * 直到该判断条件 = false时，才开始发送Observable的数据
     */
    public void skipWhile(){
        //每1s发送1个数据 = 从0开始，递增1，即0、1、2、3
        Observable.interval(1, TimeUnit.SECONDS)
                //  通过skipWhile传入一个判断条件
                .skipWhile(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        return (aLong<3);
                        // 直到判断条件不成立 = false = 发射的数据>=3，才开始发送数据
                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: 开始连接");
            }

            @Override
            public void onNext(Long aLong) {
                Log.d(TAG, "onNext: "+aLong);
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



    /**
     * 执行到某个条件时，停止发送事件
     */
    public void takeUntil(){
        //每1s发送1个数据 = 从0开始，递增1，即0、1、2、3
        Observable.interval(1, TimeUnit.SECONDS)
                //  通过takeUntil传入判断条件
                .takeUntil(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        return (aLong>3);
                        // 返回true时，就停止发送事件
                        // 当发送的数据满足>3时，就停止发送Observable的数据
                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: 开始连接");
            }

            @Override
            public void onNext(Long aLong) {
                Log.d(TAG, "onNext: "+aLong);
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
