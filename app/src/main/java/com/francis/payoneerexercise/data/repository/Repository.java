package com.francis.payoneerexercise.data.repository;

import com.francis.payoneerexercise.data.model.ListResult;

import io.reactivex.rxjava3.core.Single;

public interface Repository {
    Single<ListResult> getPaymentMethods();
}
