package com.example.newsapp.ui.main;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.newsapp.App;
import com.example.newsapp.data.remote.INewsApiClient;
import com.example.newsapp.models.Article;

import java.util.List;

public class MainViewModel extends ViewModel {

    MutableLiveData<List<Article>> newsLive = new MutableLiveData<>();
    List<Article> newsList;

    void getData() {
        App.repository.getNews("ru", "34d3aa9ece5648a188062fe1b24c84fd",
                new INewsApiClient.NewsCallBack() {
                    @Override
                    public void onSuccess(List<Article> result) {
                        newsList = result;
                        newsLive.setValue(newsList); }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
    }
}
