package br.com.baseproject.baseproject.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.baseproject.baseproject.Models.Place;
import br.com.baseproject.baseproject.R;
import br.com.baseproject.baseproject.Adapters.PlacesListAdapter;

/**
 * Created by teruya on 14/11/2017.
 */

public class SearchResultsActivity extends AppCompatActivity {

    List<Place> places;
    PlacesListAdapter adapter;
    RecyclerView placesRecyclerView;

    String referenceAddress;
    ArrayList<String> filters;
    float minPrice;
    float maxPrice;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_results);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            referenceAddress = b.getString("address");
            filters = b.getStringArrayList("filters");
            minPrice = b.getFloat("minPrice");
            maxPrice = b.getFloat("maxPrice");
        }

        places = new ArrayList<Place>();
        places.add(new Place("https://i.pinimg.com/736x/73/de/32/73de32f9e5a0db66ec7805bb7cb3f807--navy-blue-houses-blue-and-white-houses-exterior.jpg","Rua Tijuco Preto, 933, apto 73",3,1500));
        places.add(new Place("http://www.porterdavis.com.au/~/media/homes/vienna%20h/vienna%20h%2021/facades/vienna_21_albion.jpg?w=582&amp;h=320&amp;crop=1","Rua dos Pinheiros, 1090, bloco 3, apto 90",2,750));
        places.add(new Place("https://s3.amazonaws.com/uscx-media/houses/hp2/slides/slide-1.jpg","Rua Butanta,461,conjunto 21",4,1100));
        places.add(new Place("https://i.pinimg.com/736x/73/de/32/73de32f9e5a0db66ec7805bb7cb3f807--navy-blue-houses-blue-and-white-houses-exterior.jpg","Rua Tijuco Preto, 933, apto 73",3,1500));
        places.add(new Place("http://www.porterdavis.com.au/~/media/homes/vienna%20h/vienna%20h%2021/facades/vienna_21_albion.jpg?w=582&amp;h=320&amp;crop=1","Rua dos Pinheiros, 1090, bloco 3, apto 90",2,750));
        places.add(new Place("https://s3.amazonaws.com/uscx-media/houses/hp2/slides/slide-1.jpg","Rua Butanta,461,conjunto 21",4,1100));


        adapter = new PlacesListAdapter(places,this);
        placesRecyclerView = findViewById(R.id.places_list);
        placesRecyclerView.setAdapter(adapter);
        placesRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }


}
