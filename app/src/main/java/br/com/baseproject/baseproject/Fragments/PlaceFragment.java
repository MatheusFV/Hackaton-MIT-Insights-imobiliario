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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.baseproject.baseproject.Adapters.UsersListAdapter;
import br.com.baseproject.baseproject.Managers.FirebaseManager;
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
    Place myPlace;
    TextView address;
    TextView spots;
    TextView price;
    CircleImageView avatar;
    Button outButton;
    User user;

    //Firebase
    private DatabaseReference database;
    private FirebaseAuth mAuth;

    private FirebaseManager firebaseManager;
    private FirebaseManager firebaseManager2;

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


        //Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Firebase Database
        database = FirebaseDatabase.getInstance().getReference();

        DatabaseReference ref = database.child("usersGroup").child(mAuth.getCurrentUser().getUid());
        firebaseManager = new FirebaseManager(ref, new FirebaseManager.EventDataListener() {
            @Override
            public void onRefresh(DataSnapshot dataSnapshot) {
                Place place;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    place = postSnapshot.getValue(Place.class);
                    place.id = postSnapshot.getKey();

                    if (place.status.equals("confirmed")){
                        myPlace = place;
                    }
                }
                setPropertyInfo();

            }
        });

        DatabaseReference ref2 = database.child("users").child(mAuth.getCurrentUser().getUid());

        firebaseManager2 = new FirebaseManager(ref2, new FirebaseManager.EventDataListener() {
            @Override
            public void onRefresh(DataSnapshot dataSnapshot) {
                user = new User(dataSnapshot.child("name").getValue().toString(), dataSnapshot.child("photoUrl").getValue().toString(), "kicked");
            }
        });


        users = new ArrayList<>();
        users.add(new User("Rafael Teruya","https://static1.squarespace.com/static/51435924e4b02285c8b9c92d/t/558c96c3e4b03457461d0f2e/1508845725260/caiobraga-perfil.jpg",true));
        users.add(new User("Henrique Guisasola","http://newtonlimainteriores.com.br/wp-content/uploads/2016/03/home-perfil-e1484258783292.x54920.png"));
        users.add(new User("Rafael Teruya","https://static1.squarespace.com/static/51435924e4b02285c8b9c92d/t/558c96c3e4b03457461d0f2e/1508845725260/caiobraga-perfil.jpg",true));
        users.add(new User("Henrique Guisasola","http://newtonlimainteriores.com.br/wp-content/uploads/2016/03/home-perfil-e1484258783292.x54920.png"));

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
        if(myPlace != null){
            address.setText("Endereço: " + myPlace.address);
            spots.setText("Vagas: " + myPlace.slots.toString());
            price.setText("Preço: R$" + myPlace.price + ",00");

            Glide.with(this)
                    .load(myPlace.imageUrl)
                    .apply(RequestOptions.placeholderOf(R.drawable.casa_placeholder))
                    .into(avatar);
        }
    }

    void setListeners(){
        outButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = database.child("usersGroup").child(mAuth.getCurrentUser().getUid()).child(myPlace.id);
                ref.setValue(new Place(myPlace.address, "kicked"));

                DatabaseReference ref2 = database.child("placeRelations").child(myPlace.id).child(mAuth.getCurrentUser().getUid());
                ref2.setValue(user);
                Toast.makeText(getActivity(),"Você saiu do spot",Toast.LENGTH_LONG).show();
            }
        });
    }
}
