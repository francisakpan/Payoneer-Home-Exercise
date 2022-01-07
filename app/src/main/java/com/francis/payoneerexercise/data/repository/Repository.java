package com.francis.payoneerexercise.data.repository;

import com.francis.payoneerexercise.data.model.ListResult;
import com.francis.payoneerexercise.network.Network;

import io.reactivex.rxjava3.core.Single;

public class Repository {

    public Repository() { }

    public Single<ListResult> getPaymentMethods() {
        return Network
                .getInstance()
                .service()
                .getPaymentMethods();
    }
}
