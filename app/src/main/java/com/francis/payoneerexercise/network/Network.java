package com.francis.payoneerexercise.network;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {

    Network() {
    }

    private static Network instance;

    public static Network getInstance() {
        synchronized (Network.class) {
            if (instance == null) {
                instance = new Network();
            }
        }

        return instance;
    }

    private Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    public ApiService service() {
        return retrofit().create(ApiService.class);
    }
}
