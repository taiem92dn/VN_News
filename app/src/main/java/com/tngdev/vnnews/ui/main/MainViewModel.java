package com.tngdev.vnnews.ui.main;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.tngdev.vnnews.model.NewsItem;
import com.tngdev.vnnews.network.ApiResource;
import com.tngdev.vnnews.repository.NewsRepository;

import java.util.List;

public class MainViewModel extends ViewModel {

    private NewsRepository newsRepository;
    private LiveData<ApiResource<LiveData<List<NewsItem>>>> newsData;


    @ViewModelInject
    public MainViewModel(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public LiveData<ApiResource<LiveData<List<NewsItem>>>> getNews() {
        if (newsData == null) {
            newsData = newsRepository.getNews();
        }

        return newsData;
    }

    public void refreshData() {
        newsRepository.updateNewsRemote(newsData);
    }
}