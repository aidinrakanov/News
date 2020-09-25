package com.example.newsapp.data.remote;

import com.example.newsapp.data.IBaseCallback;
import com.example.newsapp.models.Article;

import java.util.List;

public interface INewsApiClient {

    void getNews (String language, String apiKey,
                  NewsCallBack callback);

    interface NewsCallBack extends IBaseCallback<List<Article>>{
        @Override
        void onSuccess(List<Article> result);

        @Override
        void onFailure(Exception e);
    }
}
