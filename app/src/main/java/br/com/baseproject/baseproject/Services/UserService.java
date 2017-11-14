package br.com.baseproject.baseproject.Services;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.baseproject.baseproject.Managers.MainManager.MainFeedback;
import br.com.baseproject.baseproject.Models.User;

/**
 * Created by giova on 12/11/2017.
 */

public class UserService {

    public interface FDUserFeedback {
        void didCreateUser(String id, boolean success);
        void didUpdateUser(boolean success);
        void didGetUser(User user, boolean success);
    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("users");

    public void createUser(User user, final FDUserFeedback feedback) {
        DatabaseReference userRef = ref.push().getRef();
        userRef.setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                feedback.didCreateUser(databaseReference.getKey(), databaseError == null);
            }
        });
    }

    public void updateUser(User user, final FDUserFeedback feedback) {
        DatabaseReference userRef = ref.child(user.id).getRef();
        //Impede o Firebase de salvar ID
        User cleanUser = user;
        cleanUser.id = null;
        userRef.setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                feedback.didUpdateUser(databaseError == null);
            }
        });
    }

    public void getUser(String id, final FDUserFeedback feedback) {
        DatabaseReference userRef = ref.child(id).getRef();
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String id = dataSnapshot.getKey();
                String name = dataSnapshot.child("name").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                feedback.didGetUser(new User(id, name, email), true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                feedback.didGetUser(null, false);
            }
        });
    }
}
