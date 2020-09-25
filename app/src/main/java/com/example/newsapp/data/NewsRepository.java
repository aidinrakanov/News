package com.example.newsapp.data;

import com.example.newsapp.data.remote.INewsApiClient;
import com.example.newsapp.models.Article;

import java.util.List;

public class NewsRepository implements INewsApiClient {


    private INewsApiClient newsApiClient;

    public NewsRepository(INewsApiClient newsApiClient) {
        this.newsApiClient = newsApiClient;
    }


    @Override
    public void getNews(String language, String apiKey, NewsCallBack callback) {
        newsApiClient.getNews(language, apiKey, new NewsCallBack() {
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
}
