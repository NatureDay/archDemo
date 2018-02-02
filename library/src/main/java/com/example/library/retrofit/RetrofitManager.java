package com.example.library.retrofit;

import android.content.Context;

import com.example.library.BaseConfig;
import com.example.library.adapter.LiveDataCallAdapterFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求封装类，以Retrofit2框架为核心
 */
public class RetrofitManager {

    private static final String HTTP_RESPONSE_CACHE = "HttpResponseCache";
    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 30 * 1024 * 1024;
    private static final int TIMEOUT_READ = 30;
    private static final int TIMEOUT_CONNECTION = 30;

    private static RetrofitManager sInstace;

    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;

    public RetrofitManager(Context context) {
        initRetrofit(context);
    }

    public static RetrofitManager getInstace(Context context) {
        if (sInstace == null) {
            synchronized (RetrofitManager.class) {
                sInstace = new RetrofitManager(context.getApplicationContext());
            }
        }
        return sInstace;
    }

    private void initRetrofit(Context context) {
        File file = new File(context.getExternalCacheDir(), HTTP_RESPONSE_CACHE);
        Cache cache = new Cache(file, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        if (BaseConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addNetworkInterceptor(interceptor);
        }
        mOkHttpClient = clientBuilder
                .addInterceptor(new CommonInterceptor())
                .addInterceptor(new TokenInterceptor(context))
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .cache(cache)
                .build();
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
                .baseUrl(BaseConfig.BASEURL)
                .client(mOkHttpClient)
                .build();
    }

    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

}
