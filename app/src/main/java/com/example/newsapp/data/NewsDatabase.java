package com.example.newsapp.data;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.newsapp.data.local.NewsDao;
import com.example.newsapp.models.Article;

@Database(entities =
        {Article.class}, version = 1,exportSchema = false)


public abstract class NewsDatabase extends RoomDatabase {
    public abstract NewsDao dao();
}
