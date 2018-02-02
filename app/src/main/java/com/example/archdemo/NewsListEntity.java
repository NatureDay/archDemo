package com.example.archdemo;

import com.google.gson.annotations.SerializedName;

/**
 * Author: Administrator
 * Time: 2017/4/1 10:37
 */
public class NewsListEntity {

    @SerializedName("create_date")
    private String createDate;

    @SerializedName("news_id")
    private long newsId;

    @SerializedName("news_source")
    private String newsSource;

    @SerializedName("title")
    private String title;

    @SerializedName("title_image")
    private String titleImage;

    private boolean isReaded = false;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public long getNewsId() {
        return newsId;
    }

    public void setNewsId(long newsId) {
        this.newsId = newsId;
    }

    public String getNewsSource() {
        return newsSource;
    }

    public void setNewsSource(String newsSource) {
        this.newsSource = newsSource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public boolean isReaded() {
        return isReaded;
    }

    public void setReaded(boolean readed) {
        isReaded = readed;
    }
}
