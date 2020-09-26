package com.example.newsapp.ui.main;

import android.os.Handler;
import android.widget.ProgressBar;

import androidx.lifecycle.LiveData;
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
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    LiveData<List<Article>> listLiveData = App.storage.getAllLive();

    List<Article> newsList;

    void setIsLoading(){
        isLoading.setValue(true);
    }

    void getData(int page, int items) {
        App.repository.getNews("ru", "e9f0e3118ad44e7c985b758271b4ebdb", page, items,
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
