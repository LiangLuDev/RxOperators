package com.luliang.rxpractice.operators.combine;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by LuLiang on 2018/5/15.
 * 组合多个被观察者
 * 按数量：zip
 * 按时间：combineLatest、combineLatestDelayError
 * 组合一个事件发送：reduce、collect
 * <p>
 * 对多个被观察者中的事件进行合并处理
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */
public class ObservablesCombine {

    private static final String TAG = "ObservablesCombine";

    /**
     * 组合 多个被观察者（Observable）发送的事件，生成一个新的事件序列（即组合过后的事件序列），并最终发送
     * ps.  1.事件组合方式 = 严格按照原先事件序列 进行对位合并
     * 2.最终合并的事件数量 = 多个被观察者（Observable）中数量最少的数量
     *
     * 使用场景：合并网络请求的发送&统一显示结果
     */
    public void zip() {
        //创建第一个被观察者
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                Thread.sleep(1000);
                emitter.onNext(2);
                Thread.sleep(1000);
                emitter.onNext(3);

                emitter.onComplete();
            }
        })//第一个被观察者设置线程
                .subscribeOn(Schedulers.io());

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("A");
                Thread.sleep(1000);
                emitter.onNext("B");
                Thread.sleep(1000);
                emitter.onNext("C");
                Thread.sleep(1000);
                emitter.onNext("D");
                emitter.onComplete();
            }
        })//第二个被观察者设置线程 假设不作线程控制，则该两个被观察者会在同一个线程中工作，即发送事件存在先后顺序，而不是同时发送
                .subscribeOn(Schedulers.newThread());


        //使用zip操作符进行事件合并
        //注：创建BiFunction对象传入的第3个参数 = 合并后数据的数据类型
        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: 开始连接");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: " + s);
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
