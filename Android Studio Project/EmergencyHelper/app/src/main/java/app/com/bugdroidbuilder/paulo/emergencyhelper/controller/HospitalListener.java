package app.com.bugdroidbuilder.paulo.emergencyhelper.controller;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.HashSet;
import java.util.Set;

import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;

/**
 * Created by pedro on 06/07/16.
 */
public class HospitalListener implements ValueEventListener {

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        Set<Hospital> instance = new HashSet<>();
        for(DataSnapshot snapshot : dataSnapshot.getChildren()){

            Hospital hospital = snapshot.getValue(Hospital.class);
            instance.add(hospital);
        }

        EventBus.getDefault().post(instance);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
    "a".toString();
    }

}
