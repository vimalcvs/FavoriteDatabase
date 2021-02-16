package com.example.libeery.api;

import com.example.libeery.model.Beers;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SecondApi {

    @GET("apps/fast-english/beers.json")
    Call<Beers> getBeers();
}
