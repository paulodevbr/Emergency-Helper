package app.com.bugdroidbuilder.paulo.emergencyhelper.service;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.HospitalListener;

/**
 * Created by pedro on 17/06/16.
 */
public class FirebaseController {

    private static FirebaseDatabase fireDb = FirebaseDatabase.getInstance();

    public static void eventGetHospital(){

        HospitalListener listener = new HospitalListener();
        DatabaseReference captain = fireDb.getReference().child("Hospitais");
        captain.addValueEventListener(listener);
    }

}
