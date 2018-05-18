package com.luliang.rxpractice.operators.filter;

import android.annotation.SuppressLint;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by LuLiang on 2018/5/18.
 * 根据 指定事件位置 过滤事件
 * <p>
 * firstElement、lastElement、elementAt、elementAtOrError
 *
 * @author LuLiang
 * @github https://github.com/LiangLuDev
 */
public class LocationFilter {

    private static final String TAG = "LocationFilter";

    /**
     * 选取第一个元素
     */
    @SuppressLint("CheckResult")
    public void firstElement() {
        Observable.just(1, 2, 3, 4, 5)
                .firstElement()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: " + integer);
                    }
                });
    }

    /**
     * 选取最后一个元素
     */
    @SuppressLint("CheckResult")
    public void lastElement() {
        Observable.just(1, 2, 3, 4, 5)
                .lastElement()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: " + integer);
                    }
                });
    }

    /**
     * 选取指定元素（通过索引确定位置）
     */
    @SuppressLint("CheckResult")
    public void elementAt(){
        //获取位置索引 = 2的 元素
        Observable.just(1,2,3,4,5)
                //根据索引值 索引从0开始  获取位置索引 = 2的 元素
                .elementAt(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: "+integer);
                    }
                });

        //获取的位置索引 ＞ 发送事件序列长度时，设置默认参数
        Observable.just(1,2,3,4,5)
                //获取的索引超出发送事件长度  设置默认参数
                .elementAt(7,6)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: "+integer);
                    }
                });
    }

    /**
     *在elementAt（）的基础上，当出现越界情况（即获取的位置索引 ＞ 发送事件序列长度）时，即抛出异常
     */
    @SuppressLint("CheckResult")
    public void elementAtOrError(){
        Observable.just(1, 2, 3, 4, 5)
                .elementAtOrError(6)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept( Integer integer) throws Exception {
                        Log.d(TAG,"获取到的事件元素是： "+ integer);
                    }
                });
    }




}
