package com.example.library.retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.library.BaseConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 拦截所有请求，向请求中添加token
 * Group:  阡陌科技
 * Author: daiyuanhong
 * Time:   2017/11/27 17:06
 */
class TokenInterceptor implements Interceptor {

    private static final String API_REQUEST = "/api/";
    private static final String API_REQUEST_TOKEN = BaseConfig.USER_TOKEN;
    private static final String AUTHORIZATION = "Authorization";

    private Context mContext;

    TokenInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request oldRequest = chain.request();
        if (oldRequest.url().toString().contains(API_REQUEST)) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            String token = sharedPreferences.getString(API_REQUEST_TOKEN, "");
            if (TextUtils.isEmpty(token)) return chain.proceed(oldRequest);
            Request.Builder newRequest = oldRequest.newBuilder();
            newRequest.header(AUTHORIZATION, token);
            return chain.proceed(newRequest.build());
        }
        return chain.proceed(oldRequest);
    }

}
