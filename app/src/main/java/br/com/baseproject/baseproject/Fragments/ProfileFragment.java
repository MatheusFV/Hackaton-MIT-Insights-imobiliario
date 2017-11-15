package br.com.baseproject.baseproject.Fragments;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import br.com.baseproject.baseproject.Adapters.ProfileAdapter;
import br.com.baseproject.baseproject.Managers.RegisterManager;
import br.com.baseproject.baseproject.Models.Place;
import br.com.baseproject.baseproject.Models.User;
import br.com.baseproject.baseproject.R;

/**
 * A simple {@link Fragment} subclass.
 */

public class ProfileFragment extends Fragment {

    private TextView nameText;
    private TextView phoneText;
    private TextView emailText;
    private Button historyButton;
    private RecyclerView userHistory;

    public ProfileFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getLayoutIds();
        setupManager();
        setupRecyclerView();
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

        userHistory = getView().findViewById(R.id.fragment_profile_history);

        nameText = getView().findViewById(R.id.fragment_profile_name);
        phoneText = getView().findViewById(R.id.fragment_profile_phone);
        emailText = getView().findViewById(R.id.fragment_profile_email);

        historyButton = getView().findViewById(R.id.fragment_profile_button_history);
    }

    private void setButtonActions() {

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setupManager() {
//        manager = new RegisterManager(this);
    }

    private void setupRecyclerView() {

        ArrayList<Place> mock = new ArrayList<Place>();
        mock.add(new Place("Rua dos Guararapes, 205"));
        mock.add(new Place("Avenida Paulista, 2770"));
        mock.add(new Place("Rua Antônio Vieira de Carvalho, 32"));

        final ProfileAdapter adapter = new ProfileAdapter(mock);
        userHistory.setAdapter(adapter);
        userHistory.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }
}
