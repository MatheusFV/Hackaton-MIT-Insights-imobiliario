package br.com.baseproject.baseproject.Fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import br.com.baseproject.baseproject.Activities.ProfilePropertyActivity;
import br.com.baseproject.baseproject.Adapters.UsersListAdapter;
import br.com.baseproject.baseproject.Models.Place;
import br.com.baseproject.baseproject.Models.User;
import br.com.baseproject.baseproject.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */

public class PlaceFragment extends Fragment {

    UsersListAdapter adapter;
    List<User> users;
    RecyclerView usersRecyclerView;
    Place place;
    TextView address;
    TextView spots;
    TextView price;
    CircleImageView avatar;
    Button outButton;

    public PlaceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_place, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLayoutsIds();



        place = new Place("https://i.pinimg.com/736x/73/de/32/73de32f9e5a0db66ec7805bb7cb3f807--navy-blue-houses-blue-and-white-houses-exterior.jpg","Rua dos Pinheiros,832","3","1500",null);
        users = new ArrayList<>();
        users.add(new User("Rafael Teruya","https://static1.squarespace.com/static/51435924e4b02285c8b9c92d/t/558c96c3e4b03457461d0f2e/1508845725260/caiobraga-perfil.jpg",true));
        users.add(new User("Henrique Guisasola","http://newtonlimainteriores.com.br/wp-content/uploads/2016/03/home-perfil-e1484258783292.x54920.png"));
        users.add(new User("Rafael Teruya","https://static1.squarespace.com/static/51435924e4b02285c8b9c92d/t/558c96c3e4b03457461d0f2e/1508845725260/caiobraga-perfil.jpg",true));
        users.add(new User("Henrique Guisasola","http://newtonlimainteriores.com.br/wp-content/uploads/2016/03/home-perfil-e1484258783292.x54920.png"));
        setPropertyInfo();
        adapter = new UsersListAdapter(users,getActivity());
        usersRecyclerView.setAdapter(adapter);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setListeners();
    }

    void getLayoutsIds(){
        usersRecyclerView = getActivity().findViewById(R.id.fragment_place_members_list);
        address = getActivity().findViewById(R.id.fragment_place_address);
        spots = getActivity().findViewById(R.id.fragment_place_spots);
        price = getActivity().findViewById(R.id.fragment_place_price);
        avatar = getActivity().findViewById(R.id.fragment_place_avatar);
        outButton = getActivity().findViewById(R.id.fragment_place_out_button);
    }

    void setPropertyInfo(){
        if(place != null){
            address.setText("Endereço: " + place.address);
            spots.setText("Vagas: " + place.slots.toString());
            price.setText("Preço: R$" + place.price + ",00");

            Glide.with(this)
                    .load(place.imageUrl)
                    .apply(RequestOptions.placeholderOf(R.drawable.casa_placeholder))
                    .into(avatar);
        }
    }

    void setListeners(){
        outButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO OUT
                Toast.makeText(getActivity(),"Você saiu do spot",Toast.LENGTH_LONG).show();
            }
        });
    }
}
