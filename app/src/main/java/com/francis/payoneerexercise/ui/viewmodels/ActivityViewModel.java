package com.francis.payoneerexercise.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.francis.payoneerexercise.data.repository.Repository;
import com.francis.payoneerexercise.data.response.Response;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ActivityViewModel extends ViewModel {
    private final Repository repository;

    public ActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    private final MutableLiveData<Response> _response = new MutableLiveData<>();
    public LiveData<Response> response = _response;

    private final CompositeDisposable disposable = new CompositeDisposable();

    public void getPaymentMethods() {
        Disposable subscription = repository.getPaymentMethods()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> _response.postValue(Response.loading()))
                .subscribe(
                        listResult -> _response.postValue(Response.success(listResult)),
                        error -> _response.postValue(Response.error(error))
                );

        disposable.add(subscription);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}