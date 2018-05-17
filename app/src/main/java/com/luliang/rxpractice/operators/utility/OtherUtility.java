package com.luliang.rxpractice.operators.utility;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by LuLiang on 2018/5/17.
 * <p>
 * 其他功能性操作符
 * <p>
 * 延迟操作：delay
 * 重复发送操作：repeat、repeatWhen
 * 在事件的生命周期中操作：do
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */
public class OtherUtility {

    private static final String TAG = "OtherUtility";

    /**
     * 使得被观察者延迟一段时间再发送事件
     */
    @SuppressLint("CheckResult")
    public void delay() {
        Observable.just(1, 2, 3)
                // 参数1 = 时间；参数2 = 时间单位
                .delay(3, TimeUnit.SECONDS)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: " + integer);
                    }
                });
    }

    /**
     * do 在某个事件的生命周期中调用
     * doOnEach  当Observable每发送1次数据事件就会调用1次
     * doOnNext  执行Next事件前调用
     * doAfterNext  执行Next事件后调用
     * doOnError  Observable发送错误事件时调用
     * doOnCompleted  Observable正常发送事件完毕后调用
     * doOnTerminate  Observable发送事件完毕后调用，无论正常发送完毕 / 异常终止
     * doFinally   最后执行
     * doOnSubscribe 观察者订阅时调用
     * doOnUnsubscribe 观察者取消订阅时调用
     */
    @SuppressLint("CheckResult")
    public void Do() {

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onError(new Throwable("发送错误"));
            }
        })
                //当Observable每发送1次数据事件就会调用1次
                .doOnEach(new Consumer<Notification<Integer>>() {
                    @Override
                    public void accept(Notification<Integer> notification) throws Exception {
                        Log.d(TAG, "doOnEach: " + notification.getValue());
                    }
                })
                //执行onNext事件前调用
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "doOnNext: " + integer);
                    }
                })
                //执行onNext事件后调用
                .doAfterNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "doAfterNext: " + integer);
                    }
                })
                //Observable正常发送事件完毕后调用
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, "doOnComplete: ");
                    }
                })
                //  Observable发送错误事件时调用
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "doOnError: " + throwable.getMessage());
                    }
                })
                //观察者订阅时调用
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.d(TAG, "doOnSubscribe: ");
                    }
                })
                //Observable发送事件完毕后调用，无论正常发送完毕 / 异常终止
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, "doAfterTerminate: ");
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, "doFinally: ");
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
     * 无条件地、重复发送被观察者事件
     * 具备重载方法，可设置重复创建次数
     * <p>
     * 1. 接收到.onCompleted()事件后，触发重新订阅 & 发送
     * 2. 默认运行在一个新的线程上
     */
    @SuppressLint("CheckResult")
    public void repeat() {

        Observable.just(1, 2, 3, 4)
                //不传入参数=无限次
                .repeat(3)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: " + integer);
                    }
                });
    }

    /**
     * 有条件地、重复发送被观察者事件
     */
    @SuppressLint("CheckResult")
    public void repeatWhen() {
        Observable.just(1, 2, 3, 4)
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {

                    //在Function函数中，必须对输入的 Observable<Object>进行处理，这里我们使用的是flatMap操作符接收上游的数据
                    @Override
                    public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                        // 将原始 Observable 停止发送事件的标识（Complete（） /  Error（））转换成1个 Object 类型数据传递给1个新被观察者（Observable）
                        // 以此决定是否重新订阅 & 发送原来的 Observable
                        // 此处有2种情况：
                        // 1. 若新被观察者（Observable）返回1个Complete（） /  Error（）事件，则不重新订阅 & 发送原来的 Observable
                        // 2. 若新被观察者（Observable）返回其余事件，则重新订阅 & 发送原来的 Observable

                        return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Object o) throws Exception {
                                // 情况1：若新被观察者（Observable）返回1个Complete（） /  Error（）事件，则不重新订阅 & 发送原来的 Observable
                                return Observable.empty();
                                //Observable.empty() = 发送Complete事件，但不会回调观察者的onComplete（）
//                                return Observable.error(new Throwable("不再重新订阅事件"));
                                //返回Error事件 = 回调onError（）事件，并接收传过去的错误信息。

                                // 情况2：若新被观察者（Observable）返回其余事件，则重新订阅 & 发送原来的 Observable
//                                return Observable.just(1, 2);
                                // 仅仅是作为1个触发重新订阅被观察者的通知，发送的是什么数据并不重要，只要不是Complete（） /  Error（）事件
                            }
                        });
                    }
                })
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
