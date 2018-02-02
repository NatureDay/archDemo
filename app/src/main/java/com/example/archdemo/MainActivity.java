package com.example.archdemo;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DemoViewHolder viewHolder = ViewModelProviders.of(this).get(DemoViewHolder.class);
        subscribeUi(viewHolder);
    }

    private void subscribeUi(DemoViewHolder viewModel) {



    }
}
