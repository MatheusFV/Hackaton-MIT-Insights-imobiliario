package br.com.baseproject.baseproject.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import br.com.baseproject.baseproject.Adapters.PlacesListAdapter;
import br.com.baseproject.baseproject.Managers.FirebaseManager;
import br.com.baseproject.baseproject.Models.Place;
import br.com.baseproject.baseproject.R;

/**
 * Created by teruya on 14/11/2017.
 */

public class SearchResultsActivity extends AppCompatActivity {

    ArrayList<Place> places;
    PlacesListAdapter adapter;
    RecyclerView placesRecyclerView;

    String referenceAddress;
    ArrayList<String> filters;
    int minPrice;
    int maxPrice;

    //Firebase
    private DatabaseReference database;
    private FirebaseAuth mAuth;

    private FirebaseManager firebaseManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_results);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            referenceAddress = b.getString("address");
            filters = b.getStringArrayList("filters");
            minPrice = b.getInt("minPrice");
            maxPrice = b.getInt("maxPrice");
        }

        places = new ArrayList<Place>();

        //Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Firebase Database
        database = FirebaseDatabase.getInstance().getReference();

        DatabaseReference ref = database.child("places");
        firebaseManager = new FirebaseManager(ref, new FirebaseManager.EventDataListener() {
            @Override
            public void onRefresh(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    places.add(postSnapshot.getValue(Place.class));
//                    Log.e("Get Data", post.<YourMethod>());
                }
                adapter = new PlacesListAdapter(filterPlaces(places, filters),SearchResultsActivity.this);
                placesRecyclerView = findViewById(R.id.places_list);
                placesRecyclerView.setAdapter(adapter);
                placesRecyclerView.setLayoutManager(new LinearLayoutManager(SearchResultsActivity.this));
            }
        });


    }

    private ArrayList<Place> filterPlaces(ArrayList<Place> places, ArrayList<String> filters) {
        return filters;
    }

}
