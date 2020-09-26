package com.example.newsapp.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.newsapp.App;
import com.example.newsapp.R;
import com.example.newsapp.models.Article;
import com.example.newsapp.ui.details.DetailsActivity;
import com.example.newsapp.ui.main.adapter.MainAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MainAdapter adapter;
    ProgressBar progressBar;
    int page = 1, items = 10;
    NestedScrollView scrollView;
    MainViewModel mViewModel;
    SwipeRefreshLayout swipeRefreshLayout;
    private List<Article> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        recyclerView = findViewById(R.id.main_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        list = new ArrayList<>();
        adapter = new MainAdapter(list);
        recyclerView.setAdapter(adapter);
        progressBar = findViewById(R.id.main_progress);
        putData();
        getData();
        paging();
        swipe();
        mViewModel.setIsLoading();

        mViewModel.getData(page, items);
        mViewModel.newsLive.observe(this, result -> {
            list.addAll(result);
            adapter.notifyDataSetChanged();
        });
    }

    private void swipe() {
        swipeRefreshLayout = findViewById(R.id.main_swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new CountDownTimer(2000, 2000) {
                    @Override
                    public void onTick(long l) {}
                    @Override
                    public void onFinish() {
                        list.clear();
                        mViewModel.getData(page, items);
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }.start();
            }
        });
    }

    private void getData() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
            App.database.dao().deleteAll();
            mViewModel.getData(page, items);
            mViewModel.newsLive.observe(this, result -> {
                App.database.dao().insert((Article) result);
                list.addAll(App.database.dao().getAll());
                adapter.updateAdapter(list);
            });
            Log.d("ololo", "Internet is connected");
        } else {
            mViewModel.listLiveData.observe(this, articles -> {
                if (articles != null) {
                    adapter.updateAdapter(articles);
                    list = articles;
                }
            });
            Log.d("ololo", "Internet is disconected");
        }
        mViewModel.isLoading.observe(this, aBoolean -> {
            if (aBoolean) progressBar.setVisibility(View.GONE);
        });
    }

    private void paging() {
        scrollView = findViewById(R.id.main_scroll);
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollX == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    page++;
                    items = +10;
                    progressBar.setVisibility(View.VISIBLE);
                    mViewModel.getData(page, items);
                }
            }
        });
    }

    private void putData() {
        adapter.setOnItemClickListener(pos -> {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("position", list.get(pos));
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }
}