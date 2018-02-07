package com.example.archdemo;

import android.app.Application;

import com.example.library.retrofit.RetrofitManager;

/**
 * Group:  阡陌科技
 * Author: daiyuanhong
 * Time:   2018/2/5 15:27
 */
public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitManager.init(this);

//        final DatabaseCreator databaseCreator = DatabaseCreator.getInstance(this);
//        databaseCreator.createDb(this);
    }
}
