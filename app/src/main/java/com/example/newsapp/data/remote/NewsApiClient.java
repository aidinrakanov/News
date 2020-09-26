package com.example.newsapp.data.remote;

import com.example.newsapp.models.News;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class NewsApiClient implements INewsApiClient {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

     public NewsApi client = retrofit.create(NewsApi.class);

    @Override
    public void getNews(String language, String apiKey, int page, int items, final NewsCallBack callback) {
        Call<News> call = client.getNews(language, apiKey, page, items);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body() != null){
                    callback.onSuccess(response.body().getArticles());
                } else {
                    callback.onFailure(new Exception("Response is empty" + response.code()));
                }

            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                callback.onFailure(new Exception(t));
            }
        });
    }

        public interface NewsApi{
        @GET("v2/top-headlines")
        Call<News> getNews(
                @Query("country") String language,
                @Query("apiKey") String apiKey,
                @Query("page") int page,
                @Query("items") int items);

    }
}
