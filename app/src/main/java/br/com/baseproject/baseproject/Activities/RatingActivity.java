package br.com.baseproject.baseproject.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import br.com.baseproject.baseproject.Adapters.RatingsAdapter;
import br.com.baseproject.baseproject.Managers.FirebaseManager;
import br.com.baseproject.baseproject.R;
import br.com.baseproject.baseproject.Utils.ToolbarConfigurator;
import br.com.baseproject.baseproject.Utils.Utils;

public class RatingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    View toolbar;

    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        getLayoutIds();
        Utils.setStatusBarColor(RatingActivity.this, R.color.colorAccent);
        ToolbarConfigurator.configToolbar(toolbar,"Avaliações",R.drawable.ic_arrow_back,0,this);
        database = FirebaseDatabase.getInstance().getReference();

        setupRecyclerView();
    }

    private void getLayoutIds() {
        recyclerView = findViewById(R.id.activity_rating_recycler_view);
        toolbar = findViewById(R.id.activity_rating_toolbar);
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
