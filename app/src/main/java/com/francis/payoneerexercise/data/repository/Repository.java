package com.francis.payoneerexercise.data.repository;

import com.francis.payoneerexercise.data.model.ListResult;
import com.francis.payoneerexercise.network.Network;

import retrofit2.Call;

public class Repository {

    public Repository() { }

    public Call<ListResult> getPaymentMethods() {
        return Network
                .getInstance()
                .service()
                .getPaymentMethods();
    }
}
