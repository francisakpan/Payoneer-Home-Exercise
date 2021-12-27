package com.francis.payoneerexercise.network;

import com.francis.payoneerexercise.data.model.ListResult;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("optile/checkout-android/develop/shared-test/lists/listresult.json")
    Call<ListResult> getPaymentMethods();
}
