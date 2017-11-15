package br.com.baseproject.baseproject.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import br.com.baseproject.baseproject.Fragments.SearchFragment;
import br.com.baseproject.baseproject.Models.User;
import br.com.baseproject.baseproject.R;
import br.com.baseproject.baseproject.databinding.ItemCheckboxOptionBinding;
import br.com.baseproject.baseproject.databinding.ItemUserBinding;

/**
 * Created by teruya on 15/11/2017.
 */

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.UserViewHolder> {

    List<User> users;
    Activity activity;
    public SearchFragment searchFragment;

    public UsersListAdapter(List<User> users,Activity activity) {
        this.users = users;
        this.activity = activity;
    }

    @Override
    public UsersListAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemUserBinding binding = ItemUserBinding.inflate(inflater, parent, false);
        return new UsersListAdapter.UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final UsersListAdapter.UserViewHolder holder, final  int position) {
        final User user = users.get(position);
        Glide.with(activity)
                .load(user.photoUrl)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_user))
                .into(holder.binding.itemUserAvatar);
        holder.bind(user);

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        private final ItemUserBinding binding;

        public UserViewHolder (ItemUserBinding binding) {
            super (binding.getRoot());
            this.binding = binding;
        }
        public void bind(User item) {
            binding.setUser(item);
            binding.executePendingBindings();
        }
    }
}
