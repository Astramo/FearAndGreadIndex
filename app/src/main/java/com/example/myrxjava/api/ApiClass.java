package com.example.myrxjava.api;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;



@Module
public class ApiClass {



    @Inject
    public ApiClass() {
    }

    public String serverAddress = "https://api.alternative.me/";



    public Api getApis() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(serverAddress)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


        return retrofit.create(Api.class);
    }




    public Single<FAGResponse> getFag() {
        return getApis().getFag();
    }
}
