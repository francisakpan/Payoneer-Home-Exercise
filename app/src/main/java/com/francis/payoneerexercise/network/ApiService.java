package com.francis.payoneerexercise.network;

import com.francis.payoneerexercise.data.model.ListResult;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface ApiService {

    @GET("optile/checkout-android/develop/shared-test/lists/listresult.json")
    Single<ListResult> getPaymentMethods();
}
