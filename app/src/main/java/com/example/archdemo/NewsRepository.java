package com.example.archdemo;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.library.ApiResponse;
import com.example.library.NetworkBoundResource;
import com.example.library.Resource;
import com.example.library.retrofit.RetrofitManager;

import java.util.List;

/**
 * Group:  阡陌科技
 * Author: daiyuanhong
 * Time:   2018/2/5 15:48
 */
public class NewsRepository {

    public LiveData<Resource<List<NewsTypeEntity>>> loadData() {
        return new NetworkBoundResource<List<NewsTypeEntity>, List<NewsTypeEntity>>() {
            @Override
            protected void saveCallResult(@NonNull List<NewsTypeEntity> item) {

            }

            @Override
            protected boolean shouldFetch(@Nullable List<NewsTypeEntity> data) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<List<NewsTypeEntity>> loadFromDb() {
                return null;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<NewsTypeEntity>>> createCall() {
                return RetrofitManager.getInstace().create(NewsService.class).getNewsType();
            }
        }.asLiveData();
    }
}
