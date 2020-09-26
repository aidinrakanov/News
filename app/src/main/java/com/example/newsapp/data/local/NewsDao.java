package com.example.newsapp.data.local;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.newsapp.models.Article;
import com.example.newsapp.models.News;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM article")
    List<Article> getAll();

    @Query("SELECT * FROM article")
    LiveData<List<Article>> getAllLive();

    @Insert
    void insert(Article article);

    @Delete
    void delete(Article article);

}
