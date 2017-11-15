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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import br.com.baseproject.baseproject.Adapters.GroupsAdapter;
import br.com.baseproject.baseproject.Managers.FirebaseManager;
import br.com.baseproject.baseproject.Models.Place;
import br.com.baseproject.baseproject.R;

/**
 * A simple {@link Fragment} subclass.
 */

public class GroupsFragment extends Fragment {

    private RecyclerView recyclerView;

    //Firebase
    private DatabaseReference database;
    private FirebaseAuth mAuth;

    private FirebaseManager firebaseManager;

    private TextView placeholder;
    ArrayList<Place> places;

    public GroupsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_groups, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        places = new ArrayList<>();

        //Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Firebase Database
        database = FirebaseDatabase.getInstance().getReference();

        DatabaseReference ref = database.child("usersGroup").child(mAuth.getCurrentUser().getUid());

        firebaseManager = new FirebaseManager(ref, new FirebaseManager.EventDataListener() {
            @Override
            public void onRefresh(DataSnapshot dataSnapshot) {
                places.clear();
                Place place;
                placeholder.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    placeholder.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    place = postSnapshot.getValue(Place.class);
                    place.id = postSnapshot.getKey();
                    places.add(place);
//                    Log.e("Get Data", post.<YourMethod>());
                    setupRecyclerView();
                }
            }
        });

        getLayoutIds();
        setupManager();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void getLayoutIds() {
        recyclerView = getView().findViewById(R.id.fragment_groups_recycler_view);
        placeholder = getView().findViewById(R.id.placeholder_chat);
    }

    private void setupManager() {

    }

    private void setupRecyclerView() {



//        places.add(new Place("https://i.pinimg.com/736x/73/de/32/73de32f9e5a0db66ec7805bb7cb3f807--navy-blue-houses-blue-and-white-houses-exterior.jpg","Rua Tijuco Preto, 933, apto 73",3,1500));
//        places.add(new Place("http://www.porterdavis.com.au/~/media/homes/vienna%20h/vienna%20h%2021/facades/vienna_21_albion.jpg?w=582&amp;h=320&amp;crop=1","Rua dos Pinheiros, 1090, bloco 3, apto 90",2,750));
//        places.add(new Place("https://s3.amazonaws.com/uscx-media/houses/hp2/slides/slide-1.jpg","Rua Butanta,461,conjunto 21",4,1100));
//        places.add(new Place("https://i.pinimg.com/736x/73/de/32/73de32f9e5a0db66ec7805bb7cb3f807--navy-blue-houses-blue-and-white-houses-exterior.jpg","Rua Tijuco Preto, 933, apto 73",3,1500));
//        places.add(new Place("http://www.porterdavis.com.au/~/media/homes/vienna%20h/vienna%20h%2021/facades/vienna_21_albion.jpg?w=582&amp;h=320&amp;crop=1","Rua dos Pinheiros, 1090, bloco 3, apto 90",2,750));
//        places.add(new Place("https://s3.amazonaws.com/uscx-media/houses/hp2/slides/slide-1.jpg","Rua Butanta,461,conjunto 21",4,1100));

        final GroupsAdapter adapter = new GroupsAdapter(getActivity(), places);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }
}
