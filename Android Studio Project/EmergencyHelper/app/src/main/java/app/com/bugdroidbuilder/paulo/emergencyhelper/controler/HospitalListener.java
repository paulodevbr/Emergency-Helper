package app.com.bugdroidbuilder.paulo.emergencyhelper.controler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;

/**
 * Created by pedro on 17/06/16.
 */
public class HospitalListener implements ValueEventListener {

    private Hospital instance = null;
    private boolean getData = false;

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        instance = dataSnapshot.getValue(Hospital.class);
        this.getData = true;
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        this.getData = false;
    }

    public boolean isGetData() {
        return getData;
    }

    public Hospital getInstance() {
        return instance;
    }
}
