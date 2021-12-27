package com.francis.payoneerexercise.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.francis.payoneerexercise.data.model.ListResult;
import com.francis.payoneerexercise.data.repository.Repository;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class ActivityViewModel extends ViewModel {
    private final Repository repository;

    ActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    private final MutableLiveData<ListResult> _results = new MutableLiveData<>();
    public LiveData<ListResult> results = _results;

    private final MutableLiveData<String> _error = new MutableLiveData<>();
    public LiveData<String> error = _error;

    public void getPaymentMethods() {
        repository.getPaymentMethods().enqueue(new Callback<ListResult>() {
            @Override
            public void onResponse(@NotNull Call<ListResult> call, @NotNull Response<ListResult> response) {
                if (response.isSuccessful()) {
                    _results.postValue(response.body());
                } else {
                    handleError(new HttpException(response));
                }
            }

            @Override
            public void onFailure(@NotNull Call<ListResult> call, @NotNull Throwable t) {
                handleError(t);
            }
        });
    }

    private void handleError(Throwable cause) {
        String errorMessage = null;
        try {
            if (cause instanceof HttpException) {
                errorMessage = ((HttpException) cause).response().errorBody().string();
            } else {
                errorMessage = cause.getMessage();
            }
        } catch (Exception exception) {
            errorMessage = exception.getMessage();
        } finally {
            _error.setValue(errorMessage);
        }
    }
}