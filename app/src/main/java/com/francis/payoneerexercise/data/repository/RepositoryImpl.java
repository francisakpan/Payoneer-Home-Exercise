package com.francis.payoneerexercise.data.repository;

import com.francis.payoneerexercise.data.model.ListResult;
import com.francis.payoneerexercise.network.Network;

import io.reactivex.rxjava3.core.Single;

public class RepositoryImpl implements Repository {
    private final Network network;

    public RepositoryImpl(Network network) {
        this.network = network;
    }

    @Override
    public Single<ListResult> getPaymentMethods() {
        return network
                .service()
                .getPaymentMethods();
    }
}
