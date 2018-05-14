package com.luliang.rxpractice.operators.create;

import android.util.Log;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by LuLiang on 2018/5/14.
 *  创建操作符
 * defer 完整创建1个被观察者对象（Observable）
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */

public class DeferOperator {

    private static final String TAG = "DeferOperator";

    public void create() {
        Observable.defer(new Callable<ObservableSource<?>>() {
            @Override
            public ObservableSource<?> call() throws Exception {
                return null;
            }
        });
    }


}
