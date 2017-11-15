package br.com.baseproject.baseproject.Activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.ArrayList;

import br.com.baseproject.baseproject.Managers.FirebaseManager;
import br.com.baseproject.baseproject.Models.Author;
import br.com.baseproject.baseproject.Models.Chat;
import br.com.baseproject.baseproject.Models.Message;
import br.com.baseproject.baseproject.Models.User;
import br.com.baseproject.baseproject.R;
import br.com.baseproject.baseproject.Utils.ImageLoaderAS;
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

    private FirebaseManager firebaseManager;
    private FirebaseManager firebaseManagerChat;
    private User user;
    private int count = 0;
    private ArrayList<Chat> chats = new ArrayList<>();

    //Firebase
    private DatabaseReference database;
    private FirebaseAuth mAuth;

    boolean done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        placeId = getIntent().getStringExtra("placeId");
        address = getIntent().getStringExtra("address");
        imageUrl = getIntent().getStringExtra("imageUrl");

        //set the imageloader for the icons
        ImageLoader iconLoader = new ImageLoader() {
            @Override
            public void loadImage(final ImageView imageView, String url) {
                if (url.contains("firebase")) {
                    ImageLoaderAS.getInstance(ChatActivity.this).displayImage(url, imageView, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {}

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {}

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                            ProgressBar pb = (ProgressBar) ((RelativeLayout) view.getParent()).findViewById(R.id.progressBar);
//                            ((RelativeLayout) view.getParent()).removeView(pb);
                        }

                        @Override
                        public void onLoadingCancelled(String imageUri, View view) {}
                    });
                } else if (url.length() > 0){
                    imageView.setImageResource(Integer.parseInt(url));
                }
            }
        };

        getLayoutIds();

        //Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Firebase Database
        database = FirebaseDatabase.getInstance().getReference();

        final MessagesListAdapter<Message> adapter = new MessagesListAdapter<>(mAuth.getCurrentUser().getUid(), iconLoader);
        messagesList.setAdapter(adapter);


        DatabaseReference ref = database.child("users").child(mAuth.getCurrentUser().getUid());
        firebaseManager = new FirebaseManager(ref, new FirebaseManager.EventDataListener() {
            @Override
            public void onRefresh(DataSnapshot dataSnapshot) {
                user = new User(dataSnapshot.child("name").getValue().toString(), dataSnapshot.child("photoUrl").getValue().toString());
            }
        });

        DatabaseReference refChat = database.child("chats").child(placeId);
        firebaseManagerChat = new FirebaseManager(refChat, new FirebaseManager.EventDataListener() {
            @Override
            public void onRefresh(DataSnapshot dataSnapshot) {
                chats.clear();
                Chat chat;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    count++;
                    if (count <= dataSnapshot.getChildrenCount() && !done){
                        if (count == dataSnapshot.getChildrenCount()) done = true;
                        chat = postSnapshot.getValue(Chat.class);
                        chats.add(chat);
                        adapter.addToStart(new Message(chat.posterId, new Author(chat.posterId, chat.name, chat.photoUrl, true), chat.message), true);
//                    Log.e("Get Data", post.<YourMethod>());
                    }else if (done && count == dataSnapshot.getChildrenCount()){
                        chat = postSnapshot.getValue(Chat.class);
                        chats.add(chat);
                        adapter.addToStart(new Message(chat.posterId, new Author(chat.posterId, chat.name, chat.photoUrl, true), chat.message), true);
                        break;
                    }

                }
                count = 0;

            }
        });

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
        messageInput.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                DatabaseReference ref = database.child("chats").child(placeId);
                ref.push().setValue(new Chat(input.toString(), user.name, user.photoUrl, mAuth.getCurrentUser().getUid()));
                //validate and send message
//                adapter.addToStart(message, true);
                return true;
            }
        });
    }
}
