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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;

import br.com.baseproject.baseproject.Adapters.ProfileAdapter;
import br.com.baseproject.baseproject.Managers.FirebaseManager;
import br.com.baseproject.baseproject.Models.Place;
import br.com.baseproject.baseproject.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */

public class ProfileFragment extends Fragment {

    private TextView nameText;
    private TextView phoneText;
    private TextView emailText;
    private Button historyButton;
    private RecyclerView userHistory;
    private CircleImageView image;

    //Firebase
    private DatabaseReference database;
    private FirebaseAuth mAuth;

    private FirebaseManager firebaseManager;

    KProgressHUD progressHUD;

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

        //Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Firebase Database
        database = FirebaseDatabase.getInstance().getReference();

        progressHUD = KProgressHUD.create(getActivity()).setDimAmount(0.5F).setCancellable(false);

        progressHUD.show();
        DatabaseReference ref = database.child("users").child(mAuth.getCurrentUser().getUid());

        firebaseManager = new FirebaseManager(ref, new FirebaseManager.EventDataListener() {
            @Override
            public void onRefresh(DataSnapshot dataSnapshot) {
                setFieldsTexts(dataSnapshot.child("name").getValue().toString(),
                        dataSnapshot.child("email").getValue().toString(),
                        dataSnapshot.child("phone").getValue().toString());

                if (dataSnapshot.child("photoUrl").getValue() != null){
                    Glide.with(getActivity())
                            .load(dataSnapshot.child("photoUrl").getValue().toString())
                            .apply(RequestOptions.placeholderOf(R.color.colorPrimaryDark))
                            .into(image);
                }

                progressHUD.dismiss();
            }
        });

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

        image = getView().findViewById(R.id.fragment_profile_avatar);
        nameText = getView().findViewById(R.id.fragment_profile_name);
        phoneText = getView().findViewById(R.id.fragment_profile_phone);
        emailText = getView().findViewById(R.id.fragment_profile_email);

        historyButton = getView().findViewById(R.id.fragment_profile_button_history);
    }

    private void setFieldsTexts(String name, String email, String phone){
        nameText.setText(name);
        emailText.setText(email);
        phoneText.setText(phone);
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
