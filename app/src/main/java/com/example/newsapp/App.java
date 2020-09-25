package com.example.newsapp;

import android.app.Application;

import com.example.newsapp.data.NewsRepository;
import com.example.newsapp.data.remote.INewsApiClient;
import com.example.newsapp.data.remote.NewsApiClient;

public class App extends Application {
    public static INewsApiClient iNewsApiClient;
    public static NewsRepository repository;


    @Override
    public void onCreate() {
        super.onCreate();
        iNewsApiClient = new NewsApiClient();
        repository = new NewsRepository(iNewsApiClient);

    }
}
