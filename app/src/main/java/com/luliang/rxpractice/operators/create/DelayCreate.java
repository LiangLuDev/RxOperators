package com.luliang.rxpractice.operators.create;

import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by LuLiang on 2018/5/14.
 * 创建操作符-延迟创建
 * <p>
 * 需求场景
 * 1、定时操作：在经过了x秒后，需要自动执行y操作
 * 2、周期性操作：每隔x秒后，需要自动执行y操作
 * <p>
 * defer
 * timer
 * interval
 * intervalRange
 * range
 * rangeLong
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */

public class DelayCreate {

    private static final String TAG = "DelayCreate";
    //第1次对i赋值
    private Integer i = 10;

    /**
     * 订阅时才会创建被观察者对象
     * 动态创建被观察者对象（Observable） & 获取最新的Observable对象数据
     * ps.类似kotlin里面的lazy 延迟创建，用到的时候才去创建
     */
    public void defer() {

        // 通过defer 定义被观察者对象
        // 注：此时被观察者对象还没创建
        Observable<Integer> defer = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.just(i);
            }
        });
        //  第2次对i赋值
        i = 15;
        //观察者开始订阅
        // 注：此时，才会调用defer（）创建被观察者对象（Observable）
        defer.subscribe(new Observer<Integer>() {
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
     * 延迟指定时间后，做想要的操作（这边默认发送1个数值0（Long类型））
     * 本质 = 延迟指定时间后，调用一次 onNext(0) 一般用于检测
     */
    public void timer() {
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: 开始连接");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG, "onNext: 延时后的操作" + aLong);
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
     * 每隔指定时间 就发送 事件
     * 发送的事件序列 = 从0开始、无限递增1的的整数序列
     */
    public void interval() {
        // 参数说明：
        // 参数1 = 第1次延迟时间；
        // 参数2 = 间隔时间数字；
        // 参数3 = 时间单位；
        // 该例子发送的事件序列特点：延迟2s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个）
        Observable.interval(2, 1, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: 开始连接");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG, "onNext: " + aLong);
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
     * 每隔指定时间 就发送 事件，可指定发送的数据的数量
     * 1、发送的事件序列 = 从0开始、无限递增1的的整数序列
     * 2、作用类似于interval（），但可指定发送的数据的数量
     */
    public void intervalRange() {
        // 参数说明：
        // 参数1 = 事件序列起始点；
        // 参数2 = 事件数量；
        // 参数3 = 第1次事件延迟发送时间；
        // 参数4 = 间隔时间数字；
        // 参数5 = 时间单位
        Observable.intervalRange(2, 5, 2, 1, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: 开始连接");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG, "onNext: " + aLong);
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
     * 连续发送 1个事件序列，可指定范围
     * 1. 发送的事件序列 = 从0开始、无限递增1的的整数序列
     * 2. 作用类似于intervalRange（），但区别在于：无延迟发送事件
     */
    public void range() {
        // 参数说明：
        // 参数1 = 事件序列起始点；
        // 参数2 = 事件数量；
        // 注：若设置为负数，则会抛出异常
        // 该例子发送的事件序列特点：从3开始发送，每次发送事件递增1，一共发送10个事件
        Observable.range(3, 10)
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
