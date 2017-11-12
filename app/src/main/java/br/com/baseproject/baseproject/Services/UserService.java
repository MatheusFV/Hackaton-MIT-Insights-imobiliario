package br.com.baseproject.baseproject.Services;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by giova on 12/11/2017.
 */

public class UserService {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("users");

    UserService() {
        ref.setValue("Hello, World!");
    }
}
