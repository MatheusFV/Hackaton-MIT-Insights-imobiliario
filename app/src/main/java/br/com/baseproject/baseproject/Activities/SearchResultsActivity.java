package br.com.baseproject.baseproject.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.Normalizer;
import java.util.ArrayList;

import br.com.baseproject.baseproject.Adapters.PlacesListAdapter;
import br.com.baseproject.baseproject.Managers.FirebaseManager;
import br.com.baseproject.baseproject.Models.Place;
import br.com.baseproject.baseproject.Models.User;
import br.com.baseproject.baseproject.R;
import br.com.baseproject.baseproject.Utils.ToolbarConfigurator;
import br.com.baseproject.baseproject.Utils.Utils;

/**
 * Created by teruya on 14/11/2017.
 */

public class SearchResultsActivity extends AppCompatActivity {

    ArrayList<Place> places = new ArrayList<Place>();
    PlacesListAdapter adapter;
    RecyclerView placesRecyclerView;
    View toolbar;

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

        toolbar = findViewById(R.id.activity_search_results_toolbar);
        ToolbarConfigurator.configToolbar(toolbar,"Resultados de Busca",R.drawable.ic_arrow_back,0,this);

        Utils.setStatusBarColor(SearchResultsActivity.this, R.color.colorAccent);

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

        DatabaseReference ref = database;
        firebaseManager = new FirebaseManager(ref, new FirebaseManager.EventDataListener() {
            @Override
            public void onRefresh(DataSnapshot dataSnapshot) {
                places.clear();
                Place place;
                User user, myUser;
                int gradient = 0;

                DataSnapshot allPlaces = dataSnapshot.child("places");
                DataSnapshot allPlaceRelations = dataSnapshot.child("placeRelations");
                DataSnapshot allUsers = dataSnapshot.child("users");

                myUser = allUsers.child(mAuth.getCurrentUser().getUid()).getValue(User.class);
                for (DataSnapshot placeDS: allPlaces.getChildren()) {
                    boolean addToResult = true;
                    place = placeDS.getValue(Place.class);
                    DataSnapshot relationDS = allPlaceRelations.child(placeDS.getKey());
                    if (filters.get(0).equals("true")) {
                        if (relationDS.getChildrenCount() == 0) { addToResult = false; }
                        boolean hasConfirmed = false;
                        for (DataSnapshot userDS: relationDS.getChildren()) {
                            if (userDS.child("status").getValue().equals("confirmed")) {
                                hasConfirmed = true;
                            }
                        }
                        if (!hasConfirmed) { addToResult = false; }
                    }
                    if (filters.get(1).equals("true")) {
                        boolean hasConfirmed = false;
                        for (DataSnapshot userDS: relationDS.getChildren()) {
                            if (userDS.child("status").getValue().equals("confirmed")) {
                                hasConfirmed = true;
                            }
                        }
                        if (hasConfirmed) { addToResult = false; }
                    }
                    for (DataSnapshot userDS: relationDS.getChildren()) {
                        user = allUsers.child(userDS.getKey()).getValue(User.class);
                        if (filters.get(2).equals("true")) {
                            if (myUser.tags.age.equals(user.tags.age)) { gradient--; }
                            else { gradient++; }
                        }
                        if (filters.get(3).equals("true")) {
                            if (myUser.tags.animal == user.tags.animal) { gradient++; }
                            if (myUser.tags.smoke != user.tags.smoke) { gradient--; }
                            if (myUser.tags.ocupation == user.tags.ocupation) { gradient++; }
                        }
                    }
                    if (gradient < 0) { addToResult = false; }

                    for (String tag: place.tags) {
                        if (tag.equals("Animais") && myUser.tags.animal == true) { addToResult = false; }
                        if (tag.equals("Fumar") && myUser.tags.smoke == true) { addToResult = false; }
                        if (tag.equals("Estudantes") && myUser.tags.ocupation.equals("Estudante")) { addToResult = false; }
                        if (tag.equals("Homens") && myUser.tags.gender.equals("Masculino")) { addToResult = false; }
                        if (tag.equals("Mulheres") && myUser.tags.gender.equals("Feminino")) { addToResult = false; }
                        if (tag.equals("Menores de idade") && myUser.tags.age == "16 - 18") { addToResult = false; }
                    }

                    if (Double.valueOf(place.price) > maxPrice || Double.valueOf(place.price) < minPrice) { addToResult = false; }

                    if (getSearchString(place.address).contains(getSearchString(referenceAddress)))

                    if (addToResult) {
                        place.id = placeDS.getKey();
                        places.add(place);
                    }
                }
                adapter = new PlacesListAdapter(places,SearchResultsActivity.this);
                placesRecyclerView = findViewById(R.id.places_list);
                placesRecyclerView.setAdapter(adapter);
                placesRecyclerView.setLayoutManager(new LinearLayoutManager(SearchResultsActivity.this));
            }
        });


    }

    public String getSearchString(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toLowerCase();
    }

}
