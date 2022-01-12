package com.francis.payoneerexercise;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* Copyright 2019 Google LLC.
    SPDX-License-Identifier: Apache-2.0 */

public class LiveDataTestUtil {
    public static <T> T getOrAwaitValue(final LiveData<T> liveData) {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(@Nullable T t) {
                data[0] = t;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);

        //Don't wait indefinitely if the LiveData is not set.
        try {
            if (!latch.await(2, TimeUnit.SECONDS)) {
                throw new RuntimeException("LiveData value was never set.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //noinspection unchecked
        return (T) data[0];
    }
}