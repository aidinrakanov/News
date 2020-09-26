package com.example.newsapp;

import android.app.Application;

import androidx.room.Room;

import com.example.newsapp.data.NewsDatabase;
import com.example.newsapp.data.NewsRepository;
import com.example.newsapp.data.local.INewsStorage;
import com.example.newsapp.data.local.NewsStorage;
import com.example.newsapp.data.remote.INewsApiClient;
import com.example.newsapp.data.remote.NewsApiClient;

public class App extends Application {
    public static INewsApiClient iNewsApiClient;
    public static NewsRepository repository;
    public static NewsDatabase database;
    public static INewsStorage storage;



    @Override
    public void onCreate() {
        super.onCreate();
        iNewsApiClient = new NewsApiClient();
        database = Room.databaseBuilder(
                this,
                NewsDatabase.class,
                "bored.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        storage = new NewsStorage(database.dao());
        repository = new NewsRepository(iNewsApiClient, storage, database.dao());
    }
}
