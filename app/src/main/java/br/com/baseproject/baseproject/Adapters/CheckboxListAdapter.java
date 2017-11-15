package br.com.baseproject.baseproject.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import br.com.baseproject.baseproject.Fragments.SearchFragment;
import br.com.baseproject.baseproject.Models.PlaceModel;
import br.com.baseproject.baseproject.R;
import br.com.baseproject.baseproject.databinding.ItemCheckboxOptionBinding;
import br.com.baseproject.baseproject.databinding.ItemPlaceCardBinding;

/**
 * Created by teruy on 15/11/2017.
 */

public class CheckboxListAdapter extends RecyclerView.Adapter<CheckboxListAdapter.CheckBoxViewHolder> {

    List<String> options;
    Activity activity;
    public SearchFragment searchFragment;

    public CheckboxListAdapter(List<String> options,Activity activity) {
        this.options = options;
        this.activity = activity;
    }

    @Override
    public CheckBoxViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCheckboxOptionBinding binding = ItemCheckboxOptionBinding.inflate(inflater, parent, false);
        return new CheckBoxViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final CheckBoxViewHolder holder,final  int position) {
        final String option = options.get(position);
        holder.binding.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                searchFragment.options[position] = b;
            }
        });
        holder.bind(option);

    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public static class CheckBoxViewHolder extends RecyclerView.ViewHolder {

        private final ItemCheckboxOptionBinding binding;

        public CheckBoxViewHolder (ItemCheckboxOptionBinding binding) {
            super (binding.getRoot());
            this.binding = binding;
        }
        public void bind(String item) {
            binding.setOption(item);
            binding.executePendingBindings();
        }
    }
}

