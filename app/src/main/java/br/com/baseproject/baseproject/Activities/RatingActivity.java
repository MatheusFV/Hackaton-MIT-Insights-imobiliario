package br.com.baseproject.baseproject.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import br.com.baseproject.baseproject.Adapters.RatingsAdapter;
import br.com.baseproject.baseproject.R;

public class RatingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        getLayoutIds();
        setupManager();
        setupRecyclerView();
    }

    private void getLayoutIds() {
        recyclerView = findViewById(R.id.activity_rating_recycler_view);
    }

    private void setupManager() {

    }

    private void setupRecyclerView() {

        ArrayList<String> ratings = new ArrayList<String>();
        ratings.add("Preço Alto");
        ratings.add("Comportamento Inadequado");
        ratings.add("Compra de Residencia");
        ratings.add("Preço Alto");
        ratings.add("Comportamento Inadequado");
        ratings.add("Compra de Residencia");

        final RatingsAdapter adapter = new RatingsAdapter(ratings);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
