package com.example.newsapp.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Parcelable;

import com.example.newsapp.R;
import com.example.newsapp.models.Article;
import com.example.newsapp.ui.details.DetailsActivity;
import com.example.newsapp.ui.main.adapter.MainAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MainAdapter adapter;
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
        putData();

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
                        mViewModel.getData();
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }.start();
            }
        });

        mViewModel.getData();
        mViewModel.newsLive.observe(this, result -> {
            list.addAll(result);
            adapter.notifyDataSetChanged();
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