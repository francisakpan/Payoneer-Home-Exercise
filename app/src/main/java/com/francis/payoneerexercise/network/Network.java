package com.francis.payoneerexercise.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {

    Network() { }

    private static Network instance;

    public static Network getInstance() {
        if (instance == null) {
            synchronized (Network.class) {
                if (instance == null) {
                    instance = new Network();
                }
            }
        }

        return instance;
    }

    private Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public ApiService service() {
        return retrofit().create(ApiService.class);
    }
}
