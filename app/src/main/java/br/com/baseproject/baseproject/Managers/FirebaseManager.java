package br.com.baseproject.baseproject.Managers;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by mvenosa on 15/11/17.
 */

public class FirebaseManager {
    private static final String TAG = "FirebaseManager";

    Query query;
    ValueEventListener valueEventListener;
//    FirebaseArray firebaseArray;
//    ArrayList<DataSnapshot> outArray = new ArrayList<DataSnapshot>();
//    DataListener dataListener;

    public interface ChildDataListener {
        void onRefresh(ArrayList<DataSnapshot> dataSnapshots);
    }

    public interface EventDataListener {
        void onRefresh(DataSnapshot dataSnapshot);
    }

    public interface SingleEventDataListener {
        void onRefresh(DataSnapshot dataSnapshot);
    }

//    public void refresh() {
//        this.dataListener.onRefresh(this.firebaseArray.getSnapshots());
//    }

//    public ArrayList<DataSnapshot> getOutArray() {return outArray;}

//    public FirebaseManager(String ref, final ChildDataListener childDataListener) {
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        Query query = firebaseDatabase.getReference(ref);
//        final FirebaseArray firebaseArray = new FirebaseArray(query);
//
//        firebaseArray.setOnChangedListener(new FirebaseArray.OnChangedListener() {
//            @Override
//            public void onChanged(EventType type, int index, int oldIndex) {
//                childDataListener.onRefresh(firebaseArray.getSnapshots());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

    public FirebaseManager (DatabaseReference query, final EventDataListener eventDataListener) {
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        query = firebaseDatabase.getReference(ref);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventDataListener.onRefresh(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        query.addValueEventListener(valueEventListener);
    }

    public FirebaseManager (String ref, final EventDataListener eventDataListener, String orderByChild) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        Query query = firebaseDatabase.getReference(ref).orderByChild(orderByChild);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventDataListener.onRefresh(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public FirebaseManager (String ref, final SingleEventDataListener singleEventDataListener) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        query = firebaseDatabase.getReference(ref);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                singleEventDataListener.onRefresh(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "error");
            }
        });
    }

}