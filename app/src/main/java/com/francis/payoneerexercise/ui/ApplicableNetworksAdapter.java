package com.francis.payoneerexercise.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.francis.payoneerexercise.data.model.ApplicableNetwork;
import com.francis.payoneerexercise.databinding.ItemMethodBinding;

import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApplicableNetworksAdapter extends RecyclerView.Adapter<ApplicableNetworksAdapter.ViewHolder> {

    private List<ApplicableNetwork> networks = new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemMethodBinding binding;

        public ViewHolder(@NonNull @NotNull ItemMethodBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ApplicableNetwork network) {
            URL url = network.getLinks().get("logo");
            assert url != null;
            Glide.with(binding.getRoot().getContext())
                    .load(url.toString())
                    .into(binding.logo);

            binding.paymentMethodNameTextView.setText(network.getLabel());
        }
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemMethodBinding binding = ItemMethodBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ApplicableNetworksAdapter.ViewHolder holder, int position) {
        holder.bind(networks.get(position));
    }

    @Override
    public int getItemCount() {
        return networks.size();
    }

    public void set(List<ApplicableNetwork> networks) {
        this.networks = networks;
        notifyDataSetChanged();
    }

    public void clear() {
        networks.clear();
        notifyDataSetChanged();
    }
}
