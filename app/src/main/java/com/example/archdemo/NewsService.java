package com.example.archdemo;

import android.arch.lifecycle.LiveData;

import com.example.library.ApiResponse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;

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
    LiveData<ApiResponse<NewsListEntity>> getNews(@Query("news_type") int type, @Query("page_size") int pageSize, @Query("page") int page);

    /**
     * 获取资讯分类
     */
    @GET("noa/news/type_list")
    LiveData<ApiResponse<List<NewsTypeEntity>>> getNewsType();
}
