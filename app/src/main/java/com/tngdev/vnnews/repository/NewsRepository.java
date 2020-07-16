package com.tngdev.vnnews.repository;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tngdev.vnnews.R;
import com.tngdev.vnnews.model.Channel;
import com.tngdev.vnnews.model.NewsItem;
import com.tngdev.vnnews.model.Rss;
import com.tngdev.vnnews.network.ApiResource;
import com.tngdev.vnnews.network.NewsService;
import com.tngdev.vnnews.util.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class NewsRepository {
    private String TAG = NewsRepository.class.getSimpleName();

    @Inject
    Application application;

    private NewsService newsService;

    @Inject
    public NewsRepository(NewsService newsService) {
        this.newsService = newsService;
    }

    public LiveData<ApiResource<LiveData<List<NewsItem>>>> getNews() {
        MutableLiveData<ApiResource<LiveData<List<NewsItem>>>> result = new MutableLiveData<>();

        updateNewsRemote(result);

        return result;
    }

    public void updateNewsRemote(LiveData<ApiResource<LiveData<List<NewsItem>>>> updateData) {
        if (!(updateData instanceof MutableLiveData))
            return;

        MutableLiveData<ApiResource<LiveData<List<NewsItem>>>> result = (MutableLiveData<ApiResource<LiveData<List<NewsItem>>>>) updateData;

        LiveData<List<NewsItem>> data = null;
        if (result.getValue() != null) {
            data = result.getValue().getData();
        }
        result.setValue(new ApiResource.Loading<>(data));

        if (!Utils.isNetworkAvailable(application)) {
            result.setValue(new ApiResource.NoInternet<>(data));
        }
        else {
            LiveData<List<NewsItem>> finalData = data;
            newsService.getNews().enqueue(new Callback<Rss>() {
                @Override
                public void onResponse(@NotNull Call<Rss> call, @NotNull Response<Rss> response) {
                    if (response.body() != null && response.body().getChannel() != null) {
                        result.setValue(new ApiResource.Success<>(new MutableLiveData<>(response.body().getChannel().getNewsItems())));
                    }
                    else {
                        Log.e(TAG, "onReponse: null data response");
                        result.setValue(new ApiResource.Error<>(finalData, application.getString(R.string.wrong_data_message)));
                    }
                }

                @Override
                public void onFailure(@NotNull Call<Rss> call, @NotNull Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage());
                    result.setValue(new ApiResource.Error<>(
                            finalData,
                            TextUtils.isEmpty(t.getMessage()) ? application.getString(R.string.unknown_error_message) : t.getMessage()
                    ));

                }
            });
        }


    }

}
