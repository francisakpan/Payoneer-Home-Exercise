package com.francis.payoneerexercise.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.francis.payoneerexercise.R;
import com.francis.payoneerexercise.data.model.ListResult;
import com.francis.payoneerexercise.data.repository.Repository;
import com.francis.payoneerexercise.data.repository.RepositoryImpl;
import com.francis.payoneerexercise.data.response.Response;
import com.francis.payoneerexercise.databinding.ActivityMainBinding;
import com.francis.payoneerexercise.network.Network;
import com.francis.payoneerexercise.ui.viewmodels.ActivityViewModel;
import com.francis.payoneerexercise.ui.viewmodels.ActivityViewModelFactory;

import java.net.UnknownHostException;

import retrofit2.HttpException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ActivityViewModel viewModel;

    private final ApplicableNetworksAdapter adapter = new ApplicableNetworksAdapter();

    private AlertDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbarBearer.toolbar);

        //Set up dialog
        setDialog();

        //Instantiate network
        Network network = new Network();

        //Instantiate view model
        Repository repository = new RepositoryImpl(network);
        viewModel = new ViewModelProvider(this, new ActivityViewModelFactory(repository))
                .get(ActivityViewModel.class);

        //Set recycler view adapter
        binding.paymentMethodRecyclerView.setAdapter(adapter);

        //Set swipe to refresh listener
        binding.swipeRefreshLayout.setOnRefreshListener(this::onRefresh);

        //observe response livedata
        viewModel.response.observe(this, this::onChanged);

        //Invoke getPaymentMethods to populate recycler view with payment methods.
        viewModel.getPaymentMethods();
    }

    private void setDialog() {
        dialog = new AlertDialog.Builder(this)
                .setTitle("Error!")
                .setIcon(R.drawable.ic_error)
                .setPositiveButton("Dismiss", (dialog, which) -> dialog.dismiss())
                .create();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
    }

    /**
     * Invoked on swipe to refresh
     */
    private void onRefresh() {
        binding.notifyTextView.setVisibility(View.GONE);
        viewModel.getPaymentMethods();
    }

    /**
     * Invoked when data is retrieved successful from network.
     *
     * @param response network response.
     */
    private void onChanged(Response response) {
        switch (response.status) {
            case LOADING:
                setLoadingState();
                break;

            case SUCCESS:
                setDataState(response.data);
                break;

            case ERROR:
                setErrorState(response.error);
                break;
        }
    }

    private void setLoadingState() {
        adapter.clear();
        binding.swipeRefreshLayout.setRefreshing(true);
    }

    private void setDataState(@Nullable ListResult data) {
        binding.swipeRefreshLayout.setRefreshing(false);
        assert data != null;
        onData();
        adapter.set(data.getNetworks().getApplicable());
    }

    private void setErrorState(Throwable error) {
        binding.swipeRefreshLayout.setRefreshing(false);
        binding.notifyTextView.setVisibility(View.VISIBLE);
        handleError(error);
    }

    private void onData() {
        if (dialog.isShowing()) dialog.dismiss();
        binding.notifyTextView.setVisibility(View.GONE);
    }

    private void handleError(Throwable cause) {
        String errorMessage = null;
        try {
            if (cause instanceof HttpException) {
                errorMessage = ((HttpException) cause).response().errorBody().string();
            } else if (cause instanceof UnknownHostException) {
                errorMessage = getString(R.string.internet_not_connected_message);
            } else {
                errorMessage = cause.getMessage();
            }
        } catch (Exception exception) {
            errorMessage = exception.getMessage();
        } finally {
            dialog.setMessage(errorMessage);
            dialog.show();
        }
    }
}