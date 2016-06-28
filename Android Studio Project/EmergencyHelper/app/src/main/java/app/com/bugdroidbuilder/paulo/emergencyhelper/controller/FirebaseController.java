package app.com.bugdroidbuilder.paulo.emergencyhelper.controller;

import android.graphics.Bitmap;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;

import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;

/**
 * Created by pedro on 17/06/16.
 */
public class FirebaseController {

    private static FirebaseDatabase fireDb = FirebaseDatabase.getInstance();
    private static FirebaseStorage fireSt = FirebaseStorage.getInstance();
    public static Hospital getHospital(String id){

        HospitalListener listener = new HospitalListener();
        DatabaseReference captain = fireDb.getReference().child("Hospitais").child(id);
        captain.addValueEventListener(listener);

        try {
            while (!listener.isGetData()) {
                Thread.sleep(250);
            }
        }catch(InterruptedException e){}

        return (listener.getInstance());
    }

    public static Bitmap getImageByHospital(Hospital hospital, String flag){

        StorageReference storageRegerence = fireSt.getReference().child("Hospitais")
                .child(hospital.getStorageId());

        Map<String,String> fotos = hospital.getFotos();
        String link = fotos.get(flag);

        storageRegerence = storageRegerence.child(link);

        BitmapListener bitListener = new BitmapListener();

        storageRegerence.getBytes(Long.MAX_VALUE).addOnSuccessListener(bitListener)
                .addOnFailureListener(bitListener);

        try {
            while (!bitListener.isGetData()) {
                Thread.sleep(250);
            }
        }catch(InterruptedException e){}

        return(bitListener.getInstance());
    }
}
