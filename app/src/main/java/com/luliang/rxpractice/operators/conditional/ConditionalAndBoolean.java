package com.luliang.rxpractice.operators.conditional;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by LuLiang on 2018/5/18.
 * <p>
 * 条件/布尔操作符
 * 通过设置函数，判断被观察者（Observable）发送的事件是否符合条件
 * <p>
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
    public void all() {
        Observable.just(1, 2, 3, 4, 5)
                .all(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return (integer <= 4);
                    }
                })
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.d(TAG, "accept: " + aBoolean);
                    }
                });
    }

    /**
     * 判断发送的每项数据是否满足 设置函数条件
     * 若发送的数据满足该条件，则发送该项数据；否则不发送
     */
    public void takeWhile() {
        //每1s发送1个数据 = 从0开始，递增1，即0、1、2、3
        Observable.interval(1, TimeUnit.SECONDS)
                //  通过takeWhile传入一个判断条件
                .takeWhile(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        return (aLong < 3);
                        // 当发送的数据满足<3时，才发送Observable的数据
                    }
                }).subscribe(new Observer<Long>() {
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
     * 判断发送的每项数据是否满足 设置函数条件
     * 直到该判断条件 = false时，才开始发送Observable的数据
     */
    public void skipWhile() {
        //每1s发送1个数据 = 从0开始，递增1，即0、1、2、3
        Observable.interval(1, TimeUnit.SECONDS)
                //  通过skipWhile传入一个判断条件
                .skipWhile(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        return (aLong < 3);
                        // 直到判断条件不成立 = false = 发射的数据>=3，才开始发送数据
                    }
                }).subscribe(new Observer<Long>() {
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
     * 执行到某个条件时，停止发送事件
     */
    public void takeUntil() {

        Observer<Long> observer = new Observer<Long>() {
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
        };

        //每1s发送1个数据 = 从0开始，递增1，即0、1、2、3
        Observable.interval(1, TimeUnit.SECONDS)
                //  通过takeUntil传入判断条件
                .takeUntil(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        return (aLong > 3);
                        // 返回true时，就停止发送事件
                        // 当发送的数据满足>3时，就停止发送Observable的数据
                    }
                }).subscribe(observer);

        //该判断条件也可以是Observable 即 等到 takeUntil（） 传入的Observable开始发送数据，（原始）第1个Observable的数据停止发送数据
        //当第 5s 时，第2个 Observable 开始发送数据，于是（原始）第1个 Observable 停止发送数据
        Observable.interval(1, TimeUnit.SECONDS)
                // 第2个Observable：延迟5s后开始发送1个Long型数据
                .takeUntil(Observable.timer(5, TimeUnit.SECONDS))
                .subscribe(observer);

    }

    /**
     * 等到 skipUntil（） 传入的Observable开始发送数据，（原始）第1个Observable的数据才开始发送数据
     */
    public void skipUntil() {
        // （原始）第1个Observable：每隔1s发送1个数据 = 从0开始，每次递增1
        //5s后（ skipUntil（） 传入的Observable开始发送数据），（原始）第1个Observable的数据才开始发送
        Observable.interval(1, TimeUnit.SECONDS)
                // 第2个Observable：延迟5s后开始发送1个Long型数据
                .skipUntil(Observable.timer(5, TimeUnit.SECONDS))
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
     * 判定两个Observables需要发送的数据是否相同
     * 若相同，返回 true；否则，返回 false
     */
    @SuppressLint("CheckResult")
    public void SequenceEqual() {
        Observable.sequenceEqual(Observable.just(4, 5, 6), Observable.just(4, 5, 6))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.d(TAG, "sequenceEqual: " + aBoolean);
                    }
                });
    }

    /**
     * 判断发送的数据中是否包含指定数据
     * 若包含，返回 true；否则，返回 false
     * 内部实现 = exists（）
     */
    @SuppressLint("CheckResult")
    public void contains() {
        Observable.just(1, 2, 3, 4, 5)
                .contains(2)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.d(TAG, "contains: " + aBoolean);
                    }
                });
    }

    /**
     * 判断发送的数据中是否为空
     * 若为空，返回 true；否则，返回 false
     */
    @SuppressLint("CheckResult")
    public void isEmpty() {
        Observable.just(1, 2, 3, 4)
                .isEmpty()
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.d(TAG, "isEmpty: " + aBoolean);
                    }
                });
    }

    /**
     * 当需要发送多个 Observable时，只发送 先发送数据的Observable的数据，而其余 Observable则被丢弃。
     */
    @SuppressLint("CheckResult")
    public void amb() {

        List<ObservableSource<Integer>> sourceList = new ArrayList<>();
        // 第1个Observable延迟1秒发射数据
        sourceList.add(Observable.just(1, 2, 3).delay(1, TimeUnit.SECONDS));
        // 第2个Observable正常发送数据
        sourceList.add(Observable.just(4, 5, 6));

        // 一共需要发送2个Observable的数据
        // 但由于使用了amb（）,所以仅发送先发送数据的Observable
        // 即第二个（因为第1个延时了）
        //即只发送了先发送数据的Observable的数据 = 4，5，6
        Observable.amb(sourceList)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "amb: " + integer);
                    }
                });
    }

    /**
     * 在不发送任何有效事件（ Next事件）、仅发送了 Complete 事件的前提下，发送一个默认值
     */
    public void defaultIfEmpty() {

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onComplete();
            }
        })
                // 若仅发送了Complete事件，默认发送 值 = 10
                .defaultIfEmpty(10)
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
