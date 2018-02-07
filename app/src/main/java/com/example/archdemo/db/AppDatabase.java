package com.example.archdemo.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.archdemo.db.dao.NewsTypeDao;
import com.example.archdemo.db.entity.NewsTypeEntity;

/**
 * Group:  阡陌科技
 * Author: daiyuanhong
 * Time:   2018/2/7 14:22
 */
@Database(entities = {NewsTypeEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "app-sample-db";

    public abstract NewsTypeDao newsTypeDao();
}
