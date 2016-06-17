package app.com.bugdroidbuilder.paulo.emergencyhelper.controler;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;

/**
 * Created by pedro on 17/06/16.
 */
public class HospitalListener implements ValueEventListener {

    private Hospital instance = null;

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        instance = dataSnapshot.getValue(Hospital.class);
        Log.d("ICEDB", "Ok : " + instance.getNome());
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.d("ICEDB", "Error : " + databaseError.toString());
    }

    public Hospital getInstance() {
        return instance;
    }
}
