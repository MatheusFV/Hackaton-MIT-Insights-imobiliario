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

import java.util.List;

import br.com.baseproject.baseproject.Models.Place;
import br.com.baseproject.baseproject.Navigation.Coordinator;
import br.com.baseproject.baseproject.R;
import br.com.baseproject.baseproject.databinding.ItemPlaceCardBinding;

/**
 * Created by teruya on 15/11/2017.
 */

public class PlacesListAdapter extends RecyclerView.Adapter<PlacesListAdapter.PlaceViewHolder> {

    List<Place> places;
    Activity activity;

    public PlacesListAdapter(List<Place> places,Activity activity) {
        this.places = places;
        this.activity = activity;
    }

    @Override
    public PlacesListAdapter.PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemPlaceCardBinding binding = ItemPlaceCardBinding.inflate(inflater, parent, false);
        return new PlaceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final PlacesListAdapter.PlaceViewHolder holder,final  int position) {
        final Place place = places.get(position);
        holder.bind(place);
        holder.binding.placeCardAccess.setVisibility(View.GONE);
        holder.binding.placeCardPrice.setText("Preço: R$" + place.price.toString() + ",00");
        holder.binding.placeCardSpots.setText("Vagas: " + place.slots.toString());
        holder.binding.placeCardAccess.setVisibility(View.GONE);
        holder.binding.placeCardContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Coordinator.goToPropertyProfile(activity,place);
            }
        });
        Glide.with(activity)
                .load(place.imageUrl)
                .apply(RequestOptions.placeholderOf(R.drawable.casa_placeholder))
                .into(holder.placeImage);


    }

    @Override
    public int getItemCount() {
        return places.size();
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
