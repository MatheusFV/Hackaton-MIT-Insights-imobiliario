package br.com.baseproject.baseproject.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.baseproject.baseproject.R;

public class RatePersonActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button sendButton;

    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_person);

        recyclerView = findViewById(R.id.activity_rate_person_recycler_view);
        sendButton = findViewById(R.id.activity_rate_person_send);

        database = FirebaseDatabase.getInstance().getReference();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
