package com.example.newsapp.ui.main.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.models.Article;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private List<Article> list;
    private IClickListener iClickListener;

    public MainAdapter(List<Article> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_news, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(IClickListener iClickListener) {
        this.iClickListener = iClickListener;
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        TextView title, desc;
        ImageView image;


        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.main_title);
            desc = itemView.findViewById(R.id.main_desc);
            image = itemView.findViewById(R.id.main_image);
            itemView.setOnClickListener(view -> {
                iClickListener.clickListener(getAdapterPosition());
            });
        }

        public void onBind(Article article) {
            title.setText(article.getTitle());
            desc.setText(Html.fromHtml(article.getDescription()));
            Glide.with(image.getContext()).load(article.getUrlToImage()).into(image);
        }
    }
}
