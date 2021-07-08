package com.example.myrxjava.api;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface Api {
    @GET("fng/?limit=31")
    Single<FAGResponse> getFag();
}