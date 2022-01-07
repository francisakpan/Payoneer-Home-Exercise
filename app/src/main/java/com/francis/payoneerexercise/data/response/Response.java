package com.francis.payoneerexercise.data.response;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.francis.payoneerexercise.data.model.ListResult;

import static com.francis.payoneerexercise.data.response.Status.ERROR;
import static com.francis.payoneerexercise.data.response.Status.LOADING;
import static com.francis.payoneerexercise.data.response.Status.SUCCESS;

/**
 * Response holder provided to the UI
 */
public class Response {

    public final Status status;

    @Nullable
    public final ListResult data;

    @Nullable
    public final Throwable error;

    private Response(Status status, @Nullable ListResult data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static Response loading() {
        return new Response(LOADING, null, null);
    }

    public static Response success(@NonNull ListResult data) {
        return new Response(SUCCESS, data, null);
    }

    public static Response error(@NonNull Throwable error) {
        return new Response(ERROR, null, error);
    }
}
