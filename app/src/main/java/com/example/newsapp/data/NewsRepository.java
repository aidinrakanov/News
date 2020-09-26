package com.example.newsapp.data;

import androidx.lifecycle.LiveData;

import com.example.newsapp.data.local.INewsStorage;
import com.example.newsapp.data.local.NewsDao;
import com.example.newsapp.data.remote.INewsApiClient;
import com.example.newsapp.models.Article;

import java.util.List;

public class NewsRepository implements INewsApiClient, INewsStorage{


    private INewsApiClient newsApiClient;
    private INewsStorage iNewsStorage;
    private NewsDao newsDao;

    public NewsRepository(INewsApiClient newsApiClient, INewsStorage iNewsStorage, NewsDao newsDao) {
        this.newsApiClient = newsApiClient;
        this.iNewsStorage = iNewsStorage;
        this.newsDao = newsDao;
    }


    @Override
    public void getNews(String language, String apiKey, int page, int items, NewsCallBack callback) {
        newsApiClient.getNews(language, apiKey, page, items, new NewsCallBack() {
            @Override
            public void onSuccess(List<Article> result) {
                callback.onSuccess(result);
            }

            @Override
            public void onFailure(Exception e) {
                callback.onFailure(e);
            }
        });
    }
    @Override
    public LiveData<List<Article>> getAllLive() {
        return iNewsStorage.getAllLive();
    }
}
