package br.com.baseproject.baseproject.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.baseproject.baseproject.Adapters.UsersListAdapter;
import br.com.baseproject.baseproject.Managers.FirebaseManager;
import br.com.baseproject.baseproject.Models.Place;
import br.com.baseproject.baseproject.Models.User;
import br.com.baseproject.baseproject.R;
import br.com.baseproject.baseproject.Utils.ToolbarConfigurator;
import br.com.baseproject.baseproject.Utils.Utils;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by teruya on 15/11/2017.
 */

public class ProfilePropertyActivity extends AppCompatActivity {
    UsersListAdapter adapter;
    List<User> users;
    RecyclerView usersRecyclerView;
    Place place;
    TextView address;
    TextView spots;
    TextView price;
    CircleImageView avatar;
    Button interestButton;
    String placeId;
    User user;
    View toolbar;

    private FirebaseManager firebaseManager;

    //Firebase
    private DatabaseReference database;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile_property);

        getLayoutsIds();

        Utils.setStatusBarColor(ProfilePropertyActivity.this, R.color.colorAccent);

        //Firebase Database
        database = FirebaseDatabase.getInstance().getReference();


        Bundle b = getIntent().getExtras();
        if(b != null) {
            place = new Place(b.getString("imageUrl"), b.getString("address"), b.getString("slots"), b.getString("price"), null);
            placeId = b.getString("id");
        }
//        place = new Place("https://i.pinimg.com/736x/73/de/32/73de32f9e5a0db66ec7805bb7cb3f807--navy-blue-houses-blue-and-white-houses-exterior.jpg","Rua dos Pinheiros,832","3","1500",null);

        setPropertyInfo();

        DatabaseReference ref = database.child("users").child(mAuth.getCurrentUser().getUid());

        firebaseManager = new FirebaseManager(ref, new FirebaseManager.EventDataListener() {
            @Override
            public void onRefresh(DataSnapshot dataSnapshot) {
                user = new User(dataSnapshot.child("name").getValue().toString(), dataSnapshot.child("photoUrl").getValue().toString());
            }
        });

        //TODO get the place's members
        users = new ArrayList<>();
        users.add(new User("Rafael Teruya","https://static1.squarespace.com/static/51435924e4b02285c8b9c92d/t/558c96c3e4b03457461d0f2e/1508845725260/caiobraga-perfil.jpg"));
        users.add(new User("Henrique Guisasola","http://newtonlimainteriores.com.br/wp-content/uploads/2016/03/home-perfil-e1484258783292.x54920.png"));

        adapter = new UsersListAdapter(users,this);

        usersRecyclerView.setAdapter(adapter);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        setListeners();
    }

    void getLayoutsIds(){
        usersRecyclerView = findViewById(R.id.activity_property_members_list);
        address = findViewById(R.id.activity_property_address);
        spots = findViewById(R.id.activity_property_spots);
        price = findViewById(R.id.activity_property_price);
        avatar = findViewById(R.id.activity_property_avatar);
        interestButton = findViewById(R.id.activity_property_button_interest);
        toolbar = findViewById(R.id.activity_profile_toolbar);
    }

    void setPropertyInfo(){
        if(place != null){
            address.setText("Endereço: " + place.address);
            spots.setText("Vagas: " + place.slots.toString());
            price.setText("Preço: R$" + place.price + ",00");

            Glide.with(this)
                    .load(place.imageUrl)
                    .apply(RequestOptions.placeholderOf(R.drawable.casa_placeholder))
                    .into(avatar);
        }

        ToolbarConfigurator.configToolbar(toolbar,"Perfil do Spot",R.drawable.ic_arrow_back,0,this);
    }

    void setListeners(){
        interestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO interesse

                mAuth = FirebaseAuth.getInstance();
                DatabaseReference ref = database.child("usersGroup").child(mAuth.getCurrentUser().getUid()).child(placeId);
                ref.setValue(new Place(place.imageUrl, place.address, place.slots, place.price, "pending", null));

                DatabaseReference ref2 = database.child("placeRelations").child(placeId).child(mAuth.getCurrentUser().getUid());
                ref2.setValue(user);

                finish();
            }
        });
    }
}
