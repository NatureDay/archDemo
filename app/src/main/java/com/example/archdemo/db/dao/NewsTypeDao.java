package com.example.archdemo.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.archdemo.db.entity.NewsTypeEntity;

import java.util.List;

/**
 * Group:  阡陌科技
 * Author: daiyuanhong
 * Time:   2018/2/7 13:28
 */
@Dao
public interface NewsTypeDao {

    @Insert
    void insertAll(List<NewsTypeEntity> entities);

    @Query("SELECT * FROM newsTypes")
    LiveData<List<NewsTypeEntity>> getAll();

}
