package com.luliang.rxpractice.operators.create;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by LuLiang on 2018/5/14.
 * 创建操作符-快速创建&发送事件
 * <p>
 * just、fromArray、fromiterable、never、empty、error
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */

public class FastCreate {

    private static final String TAG = "FastCreate";

    /**
     * 快速创建被观察者对象
     * 直接发送传入的事件（最多只能发送10个）
     * 相当于执行了 onNext(1) onNext(2) onNext(3)
     */
    public void just() {
        Observable.just(1, 2, 3, 4).subscribe(new Observer<Integer>() {
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
     * 快速创建被观察者对象
     * 直接发送 传入的数组数据（类似just升级版   just只能传入10个元素）
     * 主要使用：数组元素遍历
     */
    public void fromArray() {
        Integer[] items = {1, 5, 7, 5, 4, 8, 1, 3, 1, 5, 6, 9, 8, 7, 4, 1, 2, 3, 6, 8, 4, 2, 1, 5};
        Observable.fromArray(items)
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
     * 快速创建被观察者对象
     * 直接发送 传入的集合数据
     * 主要使用：集合元素遍历
     */
    public void fromIterable() {
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);

        Observable.fromIterable(integers)
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
     * 此类方法用的不多 一般用于测试（知道有这个操作符就行了。不用细究）
     */
    public void other() {
        // 该方法创建的被观察者对象发送事件的特点：仅发送Complete事件，直接通知完成
        //即观察者接收后会直接调用onCompleted（）
        Observable empty = Observable.empty();

        // 该方法创建的被观察者对象发送事件的特点：仅发送Error事件，直接通知异常
        // 可自定义异常
        //即观察者接收后会直接调用onError（）
        Observable error = Observable.error(new RuntimeException());
        //该方法创建的被观察者对象发送事件的特点：不发送任何事件
        //即观察者接收后什么都不调用
        Observable<Object> never = Observable.never();
    }


}
