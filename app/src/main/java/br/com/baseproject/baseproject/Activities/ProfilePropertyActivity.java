package br.com.baseproject.baseproject.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import br.com.baseproject.baseproject.Adapters.UsersListAdapter;
import br.com.baseproject.baseproject.Models.Place;
import br.com.baseproject.baseproject.Models.User;
import br.com.baseproject.baseproject.R;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile_property);

        getLayoutsIds();

        Bundle b = getIntent().getExtras();
        if(b != null) {
            String placeId = b.getString("placeId");
        }
        place = new Place("https://i.pinimg.com/736x/73/de/32/73de32f9e5a0db66ec7805bb7cb3f807--navy-blue-houses-blue-and-white-houses-exterior.jpg","Rua dos Pinheiros,832","3","1500",null);

        setPropertyInfo();

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
    }

    void setListeners(){
        interestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO interesse
                Toast.makeText(ProfilePropertyActivity.this,R.string.interest_success_message,Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
