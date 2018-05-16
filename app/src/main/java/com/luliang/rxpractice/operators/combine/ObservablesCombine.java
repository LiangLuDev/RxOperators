package com.luliang.rxpractice.operators.combine;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by LuLiang on 2018/5/15.
 * 组合多个被观察者
 * 按数量：zip
 * 按时间：combineLatest、combineLatestDelayError
 * 组合一个事件发送：reduce、collect
 * 发送事件前追加事件 startWith startWithArray
 * 统计发送事件的数量  count
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
     * <p>
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

    /**
     * combineLatest combineLatestDelayError  没有太懂使用场景 暂不写
     */
    public void combineLatest() {
    }

    /**
     * 前2个数据聚合，然后与后1个数据继续进行聚合，依次类推
     *
     * log结果：
     * 计算数据是  1加2
     * 计算数据是  3加3
     * 计算数据是  6加4
     * 最终结果10
     *
     */
    @SuppressLint("CheckResult")
    public void reduce() {

        Observable.just(1, 2, 3, 4)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    // 在该复写方法中复写聚合的逻辑
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        Log.d(TAG, "apply: 计算数据是  " + integer + "加" + integer2);
                        // 本次聚合的逻辑是：全部数据相加起来
                        // 原理：第1次取前2个数据相加，之后每次获取到的数据 = 返回的数据+原始下1个数据
                        return integer + integer2;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: 最终结果" + integer);
                    }
                });
    }

    /**
     * 将被观察者Observable发送的数据事件收集到一个数据结构里(例  list)
     */
    @SuppressLint("CheckResult")
    public void collect(){
        Observable.just(1,2,3,4,5,6)
                .collect(
                        //创建数据结构容器，用于收集被观察者发送的数据
                        new Callable<ArrayList<Integer>>() {
                    @Override
                    public ArrayList<Integer> call() throws Exception {
                        return new ArrayList<>();
                    }
                    //对发送的数据进行收集
                }, new BiConsumer<ArrayList<Integer>, Integer>() {
                    @Override
                    public void accept(ArrayList<Integer> integers, Integer integer) throws Exception {
                        integers.add(integer);
                    }
                })
                .subscribe(new Consumer<ArrayList<Integer>>() {
                    @Override
                    public void accept(ArrayList<Integer> integers) throws Exception {
                        Log.d(TAG, "accept: 数据集合数量是"+integers.size()+"条");
                    }
                });
    }

    /**
     * 在一个被观察者发送事件前，追加发送一些数据 / 一个新的被观察者
     *
     * 接收到事件1
     * 接收到事件2
     * 接收到事件3
     * 接收到事件4
     * 接收到事件5
     * 接收到事件6
     */
    @SuppressLint("CheckResult")
    public void startWithAndArray(){
        Observable.just(5,6)
                .startWith(Observable.just(3,4))//发送前追加单个数据（事件）
                .startWithArray(1,2)//发送前追加多个数据（事件）
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: 接收到事件"+integer);
                    }
                });
    }

    /**
     * 统计被观察者发送事件的数量
     */
    @SuppressLint("CheckResult")
    public void count(){
        Observable.just(1,2,3,4)
                .count()
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d(TAG, "accept: 发送数据数量"+aLong);
                    }
                });
    }


}
