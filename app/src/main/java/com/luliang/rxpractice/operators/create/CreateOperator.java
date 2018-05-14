package com.luliang.rxpractice.operators.create;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by LuLiang on 2018/5/14.
 * 创建操作符
 * create 基本创建  完整创建1个被观察者对象（Observable）
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */

public class CreateOperator {

    private static final String TAG = "CreateOperator";

    public void create() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("1");
                emitter.onNext("1");
                emitter.onNext("1");
                emitter.onNext("1");

                emitter.onComplete();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


}
