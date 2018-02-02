package com.example.archdemo;

import org.json.JSONObject;

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
    Observable<JSONObject> getNews(@Query("news_type") int type, @Query("page_size") int pageSize, @Query("page") int page);

}
