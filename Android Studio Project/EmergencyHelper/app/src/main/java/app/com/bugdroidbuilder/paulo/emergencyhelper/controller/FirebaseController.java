package app.com.bugdroidbuilder.paulo.emergencyhelper.controller;

import android.graphics.Bitmap;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;
import java.util.Set;

import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;

/**
 * Created by pedro on 17/06/16.
 */
public class FirebaseController {

    private static FirebaseDatabase fireDb = FirebaseDatabase.getInstance();
    private static FirebaseStorage fireSt = FirebaseStorage.getInstance();

    public static Hospital getHospital(String id){

        HospitalSingleListener listener = new HospitalSingleListener();
        DatabaseReference captain = fireDb.getReference().child("Hospitais").child(id);
        captain.addValueEventListener(listener);

        try {
            while (!listener.isGetData()) {
                Thread.sleep(250);
            }
        }catch(InterruptedException e){}


        return (listener.getInstance());
    }

    public static Set<Hospital> getAllHospital(){

        HospitalCollectionListener listener = new HospitalCollectionListener();
        DatabaseReference captain = fireDb.getReference().child("Hospitais");
        captain.addValueEventListener(listener);

        try {
            while (!listener.isGetData()) {
                Thread.sleep(250);
            }
        }catch(InterruptedException e){}

        return( listener.getInstance());
    }

}
