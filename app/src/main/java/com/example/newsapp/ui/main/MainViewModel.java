package com.example.newsapp.ui.main;

import android.os.Handler;
import android.widget.ProgressBar;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.newsapp.App;
import com.example.newsapp.data.remote.INewsApiClient;
import com.example.newsapp.models.Article;

import java.util.List;

public class MainViewModel extends ViewModel {
    int page = 1, items= 10;


    MutableLiveData<List<Article>> newsLive = new MutableLiveData<>();
    List<Article> newsList;

    void getData(int page, int items) {
        App.repository.getNews("ru", "34d3aa9ece5648a188062fe1b24c84fd", page, items,
                new INewsApiClient.NewsCallBack() {
                    @Override
                    public void onSuccess(List<Article> result) {
                        newsList = result;
                        newsLive.setValue(newsList);
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
    }
}
