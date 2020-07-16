package com.tngdev.vnnews.network;

import com.tngdev.vnnews.model.Channel;
import com.tngdev.vnnews.model.Rss;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsService {

    @GET("rss/tin-moi-nhat.rss")
    Call<Rss> getNews();

}
