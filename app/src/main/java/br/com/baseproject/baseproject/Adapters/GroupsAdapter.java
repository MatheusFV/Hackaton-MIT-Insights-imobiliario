package br.com.baseproject.baseproject.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import br.com.baseproject.baseproject.Models.Place;
import br.com.baseproject.baseproject.Navigation.Coordinator;
import br.com.baseproject.baseproject.R;
import br.com.baseproject.baseproject.databinding.ItemPlaceCardBinding;

/**
 * Created by luciana on 15/11/17.
 */

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.PlaceViewHolder> {

    private ArrayList<Place> places;
    private Activity activity;

    public GroupsAdapter(Activity activity, ArrayList<Place> places) {
        this.activity = activity;
        this.places = places;
    }

    @Override
    public GroupsAdapter.PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemPlaceCardBinding binding = ItemPlaceCardBinding.inflate(inflater, parent, false);
        return new GroupsAdapter.PlaceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final GroupsAdapter.PlaceViewHolder holder, final int position) {
        final Place place = places.get(position);
        holder.bind(place);
        if (place.status.equals("pending")) {
            holder.binding.placeCardContainer.setBackgroundColor(activity.getResources().getColor(R.color.lightGray));
            holder.binding.placeCardAccess.setVisibility(View.GONE);
        }

        holder.binding.placeCardAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Coordinator.goToChat(activity, place.id, place.address, place.imageUrl);
            }
        });
        holder.binding.placeCardAddress.setText(place.address);
//        holder.binding.placeCardPrice.setText("Preço: R$" + place.price.toString() + ",00");
//        holder.binding.placeCardSpots.setText("Vagas: " + place.slots.toString());
        Glide.with(activity)
                .load(place.imageUrl)
                .apply(RequestOptions.placeholderOf(R.drawable.casa_placeholder))
                .into(holder.placeImage);
    }

    @Override
    public int getItemCount() {
        return this.places.size();
    }


    public static class PlaceViewHolder extends RecyclerView.ViewHolder {

        private final ItemPlaceCardBinding binding;
        private ImageView placeImage;
        private TextView address;
        private TextView price;
        private TextView spots;
        private ImageButton access;

        public PlaceViewHolder (ItemPlaceCardBinding binding) {
            super (binding.getRoot());
            this.placeImage = binding.placeCardImage;
            this.address = binding.placeCardAddress;
            this.spots = binding.placeCardSpots;
            this.price = binding.placeCardPrice;
            this.access = binding.placeCardAccess;
            this.binding = binding;
        }
        public void bind(Place item) {
            binding.setPlace(item);
            binding.executePendingBindings();
        }
    }
}
