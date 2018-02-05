package com.example.archdemo;

import com.google.gson.annotations.SerializedName;

/**
 * Group:  阡陌科技
 * Author: daiyuanhong
 * Time:   2017/9/25 13:51
 */
public class NewsTypeEntity {

    @SerializedName("news_type")
    private int newsType;

    @SerializedName("type_name")
    private String typeName;

    public int getNewsType() {
        return newsType;
    }

    public void setNewsType(int newsType) {
        this.newsType = newsType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
