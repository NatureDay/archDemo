package com.example.archdemo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.archdemo.db.entity.NewsTypeEntity;
import com.example.library.Resource;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DemoViewHolder.Factory factory = new DemoViewHolder.Factory(getApplication(), new NewsRepository(this));
        DemoViewHolder viewHolder = ViewModelProviders.of(this, factory).get(DemoViewHolder.class);
        subscribeUi(viewHolder);
    }

    private void subscribeUi(DemoViewHolder viewModel) {
        viewModel.getNewsData().observe(this, new Observer<Resource<List<NewsTypeEntity>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<NewsTypeEntity>> listResource) {
                Log.e("fff", "-------listResource========" + listResource.toString());
            }
        });
    }
}
