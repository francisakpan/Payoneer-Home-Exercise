package com.francis.payoneerexercise.ui.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.francis.payoneerexercise.data.repository.Repository;

import org.jetbrains.annotations.NotNull;

public class ActivityViewModelFactory implements ViewModelProvider.Factory {
    private final Repository repository;

    public ActivityViewModelFactory(Repository repository) {
        this.repository = repository;
    }


    @NonNull
    @NotNull
    @Override
    public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ActivityViewModel.class)){
            return (T) new ActivityViewModel(repository);
        } else {
            throw new IllegalArgumentException("ViewModel not found!!!");
        }
    }
}