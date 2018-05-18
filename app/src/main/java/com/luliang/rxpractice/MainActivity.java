package com.luliang.rxpractice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.luliang.rxpractice.operators.filter.ConditionFilter;
import com.luliang.rxpractice.operators.filter.CountFilter;
import com.luliang.rxpractice.operators.filter.TimeFilter;
import com.luliang.rxpractice.operators.utility.AlwaysUtility;
import com.luliang.rxpractice.operators.utility.ErrorUtility;
import com.luliang.rxpractice.operators.utility.OtherUtility;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //基本创建
//        BasisCreate basisCreate = new BasisCreate();
//        basisCreate.create();

        //快速创建
//        FastCreate fastCreate=new FastCreate();
//        fastCreate.just();
//        fastCreate.fromArray();
//        fastCreate.fromIterable();

        //延迟创建
//        DelayCreate delayCreate = new DelayCreate();
//        delayCreate.defer();
//        delayCreate.timer();
//        delayCreate.interval();
//        delayCreate.intervalRange();
//        delayCreate.range();


        //map变换
//        Transform transform=new Transform();
//        transform.map();
//        transform.flatMap();
//        transform.buffer();

        //合并
//        ObservablesMerge merge=new ObservablesMerge();
//        merge.concat();
//        merge.concatArray();
//        merge.merge();
//        merge.mergeArray();
//        merge.concatArrayDelayError();

        //组合
//        ObservablesCombine combine=new ObservablesCombine();
//        combine.zip();
//        combine.reduce();
//        combine.collect();
//        combine.startWithAndArray();
//        combine.count();


        //常用功能性
//        AlwaysUtility always = new AlwaysUtility();
//        always.subscribe();
//        always.switchSubscribes();

        //其他功能性操作符
//        OtherUtility otherUtility=new OtherUtility();
//        otherUtility.repeat();
//        otherUtility.repeatWhen();

        //错误处理
//        ErrorUtility errorUtility=new ErrorUtility();
//        errorUtility.onErrorReturn();

        //条件过滤
//        ConditionFilter conditionFilter=new ConditionFilter();
//        conditionFilter.ofType();
//        conditionFilter.skipAndSkipLast();
//        conditionFilter.distinct();
//        conditionFilter.distinctUntilChanged();

        //数量过滤
//        CountFilter countFilter=new CountFilter();
//        countFilter.take();

        //时间过滤
        TimeFilter timeFilter=new TimeFilter();
//        timeFilter.throttleFirst();
        timeFilter.throttleWithTimeout();
    }
}
