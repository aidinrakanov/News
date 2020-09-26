package com.example.newsapp.data.local;

import androidx.lifecycle.LiveData;

import com.example.newsapp.models.Article;

import java.util.List;

public class NewsStorage implements INewsStorage {

    private NewsDao dao;

    public NewsStorage(NewsDao dao){
        this.dao = dao;
    }


    public void saveNews(Article article){
        dao.insert(article);
    }

    public List<Article> getAll(){
        return dao.getAll();
    }

    public LiveData<List<Article>> getAllLive(){
        return dao.getAllLive();
    }

    public void deleteNews(Article article){
        dao.delete(article);
    }
}
