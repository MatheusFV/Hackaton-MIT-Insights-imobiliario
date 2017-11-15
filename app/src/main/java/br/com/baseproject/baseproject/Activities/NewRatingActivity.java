package br.com.baseproject.baseproject.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.baseproject.baseproject.Adapters.CheckboxListAdapter;
import br.com.baseproject.baseproject.R;
import br.com.baseproject.baseproject.Utils.ToolbarConfigurator;
import br.com.baseproject.baseproject.Utils.Utils;

/**
 * Created by teruya on 15/11/2017.
 */

public class NewRatingActivity extends AppCompatActivity {

    View toolbar;
    CheckboxListAdapter adapter;
    List<String> filters;
    RecyclerView optionsRecyclerView;
    String userId;
    public Boolean[] options;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_rating);
        Bundle b = getIntent().getExtras();
        if(b != null) {

            userId = b.getString("userId");
        }
        getLayoutIds();
        Utils.setStatusBarColor(NewRatingActivity.this, R.color.colorAccent);
        ToolbarConfigurator.configToolbar(toolbar,"Avaliação",R.drawable.ic_arrow_back,0,this);
        filters = new ArrayList<>();

        filters.add("Já existem moradores");
        filters.add("Ainda não há moradores no momento");
        filters.add("Posso morar sozinho");
        filters.add("Os moradores tem idade parecida com a minha");
        filters.add("Os moradores tem perfil similar ao meu");
        filters.add("O proprietário é membro ativo do aplicativo");

        options= new Boolean[filters.size()];
        Arrays.fill(options, Boolean.FALSE);

        adapter = new CheckboxListAdapter(filters,this);
        adapter.newRatingActivity = this;

        optionsRecyclerView.setAdapter(adapter);
        optionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setButtonActions();
    }

    private void getLayoutIds() {
        toolbar = findViewById(R.id.activity_new_rating_toolbar);
        optionsRecyclerView = findViewById(R.id.activity_new_rating_options_list);
    }

    void setButtonActions(){
        Button saveButton = findViewById(R.id.activity_new_rating_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Avaliar Usuário (userId pego da intent)
                finish();
            }
        });
    }
}
