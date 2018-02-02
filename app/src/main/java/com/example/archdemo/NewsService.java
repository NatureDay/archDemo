package com.example.archdemo;

import android.arch.lifecycle.LiveData;

import com.example.library.Resource;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Group:  阡陌科技
 * Author: daiyuanhong
 * Time:   2018/2/1 15:31
 */
public interface NewsService {

    /**
     * 获取资讯新闻列表
     */
    @GET("noa/news/news_list")
    Observable<LiveData<Resource<NewsListEntity>>> getNews(@Query("news_type") int type, @Query("page_size") int pageSize, @Query("page") int page);

}
