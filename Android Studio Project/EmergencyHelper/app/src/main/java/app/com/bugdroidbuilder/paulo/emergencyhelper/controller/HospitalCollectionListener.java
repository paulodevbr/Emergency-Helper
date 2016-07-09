package app.com.bugdroidbuilder.paulo.emergencyhelper.controller;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;

/**
 * Created by pedro on 06/07/16.
 */
public class HospitalCollectionListener implements ValueEventListener {

    private Set<Hospital> instance = new HashSet<>();
    private boolean getData = false;


    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        for(DataSnapshot snapshot : dataSnapshot.getChildren()){

            Hospital hospital = snapshot.getValue(Hospital.class);
            this.instance.add(hospital);
        }

        this.getData = true;
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        this.getData = false;
    }

    public boolean isGetData() {
        return getData;
    }

    public Set<Hospital> getInstance() {
        return instance;
    }
}
