package com.example.archdemo;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.archdemo.db.DatabaseCreator;
import com.example.archdemo.db.entity.NewsTypeEntity;
import com.example.library.AbsentLiveData;
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
//        final DatabaseCreator databaseCreator = DatabaseCreator.getInstance(application.getApplicationContext());

//        LiveData<Boolean> isDatabaseCreated =  databaseCreator.isDatabaseCreated();
//        mNewsData = Transformations.switchMap(isDatabaseCreated, new Function<Boolean, LiveData<Resource<List<NewsTypeEntity>>>>() {
//            @Override
//            public LiveData<Resource<List<NewsTypeEntity>>> apply(Boolean input) {
//                if (!input.equals(Boolean.TRUE)){
//                    return AbsentLiveData.create();
//                }else {
//                    return   databaseCreator.getDatabase().newsTypeDao().getAll();
//                }
//                return null;
//            }
//        });
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
