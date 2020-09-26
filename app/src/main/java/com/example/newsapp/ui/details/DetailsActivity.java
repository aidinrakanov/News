package com.example.newsapp.ui.details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.models.Article;

public class DetailsActivity extends AppCompatActivity {

    DetailsViewModel dViewModel;
    TextView dTitle, dDesc;
    ImageView dImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("News");

            dViewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
            dTitle = findViewById(R.id.details_title);
            dDesc = findViewById(R.id.details_desc);
            dImage = findViewById(R.id.details_image);

            if (getIntent() != null) {
                Article article = (Article) getIntent().getSerializableExtra("position");
                assert article != null;
                dTitle.setText(article.getTitle());
                    dDesc.setText(article.getDescription());
                    Glide.with(dImage.getContext()).load(article.getUrlToImage()).into(dImage);

            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }
}