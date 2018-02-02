package com.example.archdemo;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.library.Resource;

import java.util.List;

/**
 * Group:  阡陌科技
 * Author: daiyuanhong
 * Time:   2018/2/2 16:26
 */
public class DemoViewHolder extends AndroidViewModel {

    private final LiveData<Resource<List<NewsListEntity>>> mNewsData;

    public DemoViewHolder(@NonNull Application application) {
        super(application);
        mNewsData = new MutableLiveData<>();
    }

    public void start() {

    }
}
