package com.example.archdemo;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

/**
 * Group:  阡陌科技
 * Author: daiyuanhong
 * Time:   2018/2/5 16:52
 */
public class AppViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {
    /**
     * Creates a {@code AppViewModelFactory}
     *
     * @param application an application to pass in {@link AppViewModelFactory}
     */
    public AppViewModelFactory(@NonNull Application application) {
        super(application);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return super.create(modelClass);
    }
}
