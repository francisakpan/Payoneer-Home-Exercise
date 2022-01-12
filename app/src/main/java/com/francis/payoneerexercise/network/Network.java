package com.francis.payoneerexercise.network;

import java.util.concurrent.TimeUnit;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {

    public Network() {
    }

    private final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(1L, TimeUnit.MINUTES)
            .readTimeout(30L, TimeUnit.SECONDS)
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build();

    public ApiService service() {
        return retrofit.create(ApiService.class);
    }
}
