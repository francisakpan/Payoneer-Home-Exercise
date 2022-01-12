package com.francis.payoneerexercise;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.francis.payoneerexercise.data.model.ListResult;
import com.francis.payoneerexercise.data.repository.Repository;
import com.francis.payoneerexercise.data.response.Response;
import com.francis.payoneerexercise.data.response.Status;
import com.francis.payoneerexercise.rules.RxImmediateSchedulerRule;
import com.francis.payoneerexercise.ui.viewmodels.ActivityViewModel;
import com.google.common.truth.Truth;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.net.UnknownHostException;

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ActivityViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @ClassRule
    public static final RxImmediateSchedulerRule schedulerRule = new RxImmediateSchedulerRule();

    @Mock
    public Repository repository;

    private ActivityViewModel viewModel;

    @Before
    public void setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
        MockitoAnnotations.initMocks(this);
        viewModel = new ActivityViewModel(repository);
    }

    @After
    public void tearDown() {
        RxAndroidPlugins.reset();
    }

    @Test
    public void liveDataObservableHasNoValueSet() {
        Assert.assertThrows(
                RuntimeException.class,
                () -> LiveDataTestUtil.getOrAwaitValue(viewModel.response)
        );
    }

    @Test
    public void liveDataObservableHasValueSet() {
        Mockito.when(repository.getPaymentMethods()).thenReturn(Single.just(new ListResult()));

        viewModel.getPaymentMethods();

        Response value = LiveDataTestUtil.getOrAwaitValue(viewModel.response);

        Truth.assertThat(value).isNotNull();
        Truth.assertThat(value).isInstanceOf(Response.class);
    }

    @Test
    public void liveDataObservableHasSuccessData() {
        ListResult listResult = new ListResult();
        listResult.setOperationType("LIST");
        listResult.setIntegrationType("MOBILE_NATIVE");

        Mockito.when(repository.getPaymentMethods()).thenReturn(Single.just(listResult));

        viewModel.getPaymentMethods();

        Status status = LiveDataTestUtil.getOrAwaitValue(viewModel.response).status;
        ListResult data = LiveDataTestUtil.getOrAwaitValue(viewModel.response).data;
        Throwable received = LiveDataTestUtil.getOrAwaitValue(viewModel.response).error;

        Truth.assertThat(received).isNull();
        Truth.assertThat(data).isEqualTo(listResult);
        Truth.assertThat(status).isEqualTo(Status.SUCCESS);
    }

    @Test
    public void liveDataObservableHasErrorData() {
        Throwable cause = new UnknownHostException("No internet connection");
        Mockito.when(repository.getPaymentMethods()).thenReturn(Single.error(cause));

        viewModel.getPaymentMethods();

        Status status = LiveDataTestUtil.getOrAwaitValue(viewModel.response).status;
        ListResult data = LiveDataTestUtil.getOrAwaitValue(viewModel.response).data;
        Throwable received = LiveDataTestUtil.getOrAwaitValue(viewModel.response).error;

        Truth.assertThat(data).isNull();
        Truth.assertThat(status).isEqualTo(Status.ERROR);
        Truth.assertThat(received).hasMessageThat().contains("No internet connection");
    }

}
