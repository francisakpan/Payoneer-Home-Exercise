package com.francis.payoneerexercise.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.francis.payoneerexercise.R;
import com.francis.payoneerexercise.data.model.ListResult;
import com.francis.payoneerexercise.data.repository.Repository;
import com.francis.payoneerexercise.databinding.ActivityMainBinding;
import com.francis.payoneerexercise.ui.viewmodels.ActivityViewModel;
import com.francis.payoneerexercise.ui.viewmodels.ActivityViewModelFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ActivityViewModel viewModel;

    private final ApplicableNetworksAdapter adapter = new ApplicableNetworksAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbarBearer.toolbar);

        //Instantiate view model
        Repository repository = new Repository();
        viewModel = new ViewModelProvider(this, new ActivityViewModelFactory(repository))
                .get(ActivityViewModel.class);

        //Set recycler view adapter
        binding.paymentMethodRecyclerView.setAdapter(adapter);

        //Set swipe to refresh listener
        binding.swipeRefreshLayout.setOnRefreshListener(this::onRefresh);

        //observe ListResult livedata
        viewModel.results.observe(this, this::onChanged);

        //observe error livedata
        viewModel.error.observe(this, this::onChanged);

        //Invoke getPaymentMethods to populate recycler view with payment methods.
        viewModel.getPaymentMethods();
    }

    /**
     * Display AlertDialog with error message
     * @param msg error message
     */
    private void showAlertDialog(String msg) {
        new AlertDialog.Builder(this)
                .setTitle("Error!")
                .setIcon(R.drawable.ic_error)
                .setMessage(msg)
                .setPositiveButton("Dismiss", (dialog, which) -> dialog.dismiss())
                .show();
    }

    /**
     * Invoked when data is retrieved successful from network.
     * @param listResult network ListResult.
     */
    private void onChanged(ListResult listResult) {
        binding.swipeRefreshLayout.setRefreshing(false);
        binding.progressIndicator.setVisibility(View.GONE);
        adapter.set(listResult.getNetworks().getApplicable());
    }

    /**
     * Invoked on error received.
     * @param msg error message
     */
    private void onChanged(String msg) {
        binding.swipeRefreshLayout.setRefreshing(false);
        binding.progressIndicator.setVisibility(View.GONE);
        binding.notifyTextView.setVisibility(View.VISIBLE);
        showAlertDialog(msg);
    }

    /**
     * Invoked on swipe to refresh
     */
    private void onRefresh() {
        binding.notifyTextView.setVisibility(View.GONE);

        if (binding.progressIndicator.isShown()) {
            binding.swipeRefreshLayout.setRefreshing(false);
            return;
        }

        adapter.clear();
        viewModel.getPaymentMethods();
    }
}