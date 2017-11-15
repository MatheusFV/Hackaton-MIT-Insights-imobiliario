package br.com.baseproject.baseproject.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;

import br.com.baseproject.baseproject.R;
import br.com.baseproject.baseproject.Utils.ToolbarConfigurator;
import br.com.baseproject.baseproject.Utils.Utils;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    private CircleImageView groupImage;
    private TextView addressText;
    private TextView peopleText;

    private MessagesList messagesList;
    private MessageInput messageInput;

    private String placeId, address, imageUrl;
    private View toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        placeId = getIntent().getStringExtra("placeId");
        address = getIntent().getStringExtra("address");
        imageUrl = getIntent().getStringExtra("imageUrl");

        getLayoutIds();
        setInformation();
        setupManager();
        setupChat();
    }

    private void getLayoutIds() {

        groupImage = findViewById(R.id.activity_chat_image);
        addressText = findViewById(R.id.activity_chat_address);
        peopleText = findViewById(R.id.activity_chat_people);

        messagesList = findViewById(R.id.activity_chat_message_list);
        messageInput = findViewById(R.id.activity_chat_message_input);
        toolbar = findViewById(R.id.activity_chat_toolbar);
    }

    private void setInformation() {

        ToolbarConfigurator.configToolbar(toolbar,"Chat",R.drawable.ic_arrow_back,0,this);
        Utils.setStatusBarColor(ChatActivity.this, R.color.colorAccent);

        addressText.setText(address);
        Glide.with(this)
                .load(imageUrl)
                .apply(RequestOptions.placeholderOf(R.drawable.casa_placeholder)).into(groupImage);
    }

    private void setupManager() {
//        manager = new RegisterManager(this);
    }

    private void setupChat() {

    }
}
