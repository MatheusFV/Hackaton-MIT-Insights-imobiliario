package br.com.baseproject.baseproject.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import br.com.baseproject.baseproject.Models.Place;
import br.com.baseproject.baseproject.databinding.ItemHistoryBinding;
import br.com.baseproject.baseproject.databinding.ItemRatingBinding;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by luciana on 15/11/17.
 */

public class RatingsAdapter extends RecyclerView.Adapter<RatingsAdapter.RateViewHolder> {

    private ArrayList<String> ratings = new ArrayList<String>();
    private ArrayList<String> counts = new ArrayList<String>();

    public RatingsAdapter(ArrayList<String> rates) {

        for (String rate : rates) {
            boolean hasElement = false;
            for (int i = 0; i < ratings.size(); i++) {
                if (ratings.get(i).equals(rate)) {
                    hasElement = true;
                    counts.set(i, String.valueOf(Integer.valueOf(counts.get(i)) + 1));
                }
            }
            if (!hasElement) {
                ratings.add(rate);
                counts.add("1");
            }
        }
        this.ratings = ratings;
    }

    @Override
    public RatingsAdapter.RateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemRatingBinding binding = ItemRatingBinding.inflate(inflater, parent, false);
        return new RatingsAdapter.RateViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final RatingsAdapter.RateViewHolder holder, final int position) {

        holder.description.setText(ratings.get(position));
        holder.count.setText("("+counts.get(position)+")");
    }

    @Override
    public int getItemCount() {
        return this.ratings.size();
    }

    public static class RateViewHolder extends RecyclerView.ViewHolder {

        private final ItemRatingBinding binding;
        public TextView description;
        public TextView count;

        public RateViewHolder (ItemRatingBinding binding) {
            super (binding.getRoot());
            this.description = binding.itemRatingDescription;
            this.count = binding.itemRatingOccurencies;
            this.binding = binding;
        }
        public void bind(String description, String count) {
            binding.setDescription(description);
            binding.setCount(count);
            binding.executePendingBindings();
        }
    }
}
