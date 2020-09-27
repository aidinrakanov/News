package com.example.newsapp.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
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
    ProgressBar progressBar, progressBarLoading;
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
        progressBar = findViewById(R.id.main_progress);
        recycler();
        putData();
        getDataFromDB();
        paging();
        swipe();
    }

    private void recycler() {
        recyclerView = findViewById(R.id.main_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        list = new ArrayList<>();
        adapter = new MainAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    private void swipe() {
        swipeRefreshLayout = findViewById(R.id.main_swipe);
        swipeRefreshLayout.setOnRefreshListener(() -> new CountDownTimer(1500, 1500) {
            @Override
            public void onTick(long l) {}
            @Override
            public void onFinish() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }.start());
    }

    private void getDataFromDB() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {

            mViewModel.getData(page, items);
            if (App.database.dao().getAll() != null)
                App.database.dao().deleteAll();

            mViewModel.newsLive.observe(this, result -> {
                App.database.dao().insert(result);
                list.addAll(result);
                adapter.updateAdapter(list);
                progressBar.setVisibility(View.GONE);
            });
        } else {
            mViewModel.listLiveData.observe(this, articles -> {
                if (articles != null) {
                    list.addAll(articles);
                    adapter.updateAdapter(articles);
//                    progressBar.setVisibility(View.GONE);
                }
            });
        }
//        mViewModel.progressBarLoading.observe(this, aBoolean -> {
//            if (aBoolean) progressBarLoading.setVisibility(View.GONE);
//        });
    }

    private void paging() {
        scrollView = findViewById(R.id.main_scroll);
        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                if (items >= list.size()){
                    page++;
                    items = items +10;
                    progressBar.setVisibility(View.VISIBLE);
                    mViewModel.getData(page,items);
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