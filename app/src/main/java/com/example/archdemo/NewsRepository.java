package com.example.archdemo;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.archdemo.db.AppDatabase;
import com.example.archdemo.db.dao.NewsTypeDao;
import com.example.archdemo.db.entity.NewsTypeEntity;
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

    private Context mContext;

    private final NewsTypeDao mNewsTypeDao;

    public NewsRepository(Context context) {
        mContext = context.getApplicationContext();

        AppDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, AppDatabase.DATABASE_NAME).addMigrations().build();
        mNewsTypeDao = db.newsTypeDao();
//        mNewsTypeDao = DatabaseCreator.getInstance(mContext).getDatabase().newsTypeDao();
//        LiveData<Boolean> isDatabaseCreated =  mDatabaseCreator.isDatabaseCreated();
//        LiveData<Boolean> isDatabaseCreated = databaseCreator.isDatabaseCreated();
//        mNewsData = Transformations.switchMap(isDatabaseCreated, new Function<Boolean, LiveData<Resource<List<NewsTypeEntity>>>>() {
//            @Override
//            public LiveData<Resource<List<NewsTypeEntity>>> apply(Boolean input) {
//                if (!input.equals(Boolean.TRUE)) {
//                    return AbsentLiveData.create();
//                } else {
//
//                }
//                return null;
//            }
//        });
    }

    public LiveData<Resource<List<NewsTypeEntity>>> loadData() {
        return new NetworkBoundResource<List<NewsTypeEntity>, List<NewsTypeEntity>>() {
            @Override
            protected void saveCallResult(@NonNull List<NewsTypeEntity> item) {
                mNewsTypeDao.insertAll(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<NewsTypeEntity> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<NewsTypeEntity>> loadFromDb() {
                return mNewsTypeDao.getAll();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<NewsTypeEntity>>> createCall() {
                return RetrofitManager.getInstace().create(NewsService.class).getNewsType();
            }
        }.asLiveData();
    }
}
