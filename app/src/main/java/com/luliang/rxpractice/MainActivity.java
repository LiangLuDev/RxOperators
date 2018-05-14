package com.luliang.rxpractice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.luliang.rxpractice.operators.create.CreateOperator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CreateOperator operator = new CreateOperator();
        operator.create();
    }
}
