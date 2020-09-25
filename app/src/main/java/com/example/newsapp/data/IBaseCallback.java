package com.example.newsapp.data;

public interface IBaseCallback<T> {
    void onSuccess(T result);

    void onFailure(Exception e);
}
