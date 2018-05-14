package com.luliang.rxpractice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.luliang.rxpractice.operators.create.BasisCreate;
import com.luliang.rxpractice.operators.create.DelayCreate;
import com.luliang.rxpractice.operators.create.FastCreate;

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
    }
}
