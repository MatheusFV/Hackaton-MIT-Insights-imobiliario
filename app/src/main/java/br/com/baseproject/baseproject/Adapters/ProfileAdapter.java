package br.com.baseproject.baseproject.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.baseproject.baseproject.Models.Place;
import br.com.baseproject.baseproject.databinding.ItemHistoryBinding;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Giovanni on 15/11/17.
 */

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.HistoryViewHolder> {

    private ArrayList<Place> places;

    public ProfileAdapter(ArrayList<Place> places) {
        this.places = places;
    }

    @Override
    public ProfileAdapter.HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemHistoryBinding binding = ItemHistoryBinding.inflate(inflater, parent, false);
        return new HistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ProfileAdapter.HistoryViewHolder holder, final int position) {

        holder.address.setText(places.get(position).address);
    }

    @Override
    public int getItemCount() {
        return this.places.size();
    }


    public static class HistoryViewHolder extends RecyclerView.ViewHolder {

        private final ItemHistoryBinding binding;
        public CircleImageView image;
        public TextView address;

        public HistoryViewHolder (ItemHistoryBinding binding) {
            super (binding.getRoot());
            this.image = binding.itemHistoryIcon;
            this.address = binding.itemHistoryAddress;
            this.binding = binding;
        }
        public void bind(Place item) {
            binding.setPlace(item);
            binding.executePendingBindings();
        }
    }
}
