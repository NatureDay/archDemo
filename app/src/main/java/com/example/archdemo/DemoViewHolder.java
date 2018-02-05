package com.example.archdemo;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.library.Resource;

import java.util.List;

/**
 * Group:  阡陌科技
 * Author: daiyuanhong
 * Time:   2018/2/2 16:26
 */
public class DemoViewHolder extends AndroidViewModel {

    private final LiveData<Resource<List<NewsTypeEntity>>> mNewsData;

    private NewsRepository mRepository;

    public DemoViewHolder(@NonNull Application application, NewsRepository repository) {
        super(application);
        mRepository = repository;
        mNewsData = mRepository.loadData();
    }

    public LiveData<Resource<List<NewsTypeEntity>>> getNewsData() {
        return mNewsData;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final NewsRepository nRepository;

        public Factory(@NonNull Application application, NewsRepository repository) {
            this.mApplication = application;
            this.nRepository = repository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new DemoViewHolder(mApplication, nRepository);
        }
    }

}
