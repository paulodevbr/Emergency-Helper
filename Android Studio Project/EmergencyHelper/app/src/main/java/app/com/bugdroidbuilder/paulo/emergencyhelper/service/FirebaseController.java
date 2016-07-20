package app.com.bugdroidbuilder.paulo.emergencyhelper.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.HospitalListener;

/**
 * Created by pedro on 17/06/16.
 */
public class FirebaseController {

    private static FirebaseDatabase fireDb = FirebaseDatabase.getInstance();

    public FirebaseController(){
        this.fireDb.getReference().keepSynced(true);
    }

    public static void eventGetHospital(){

        HospitalListener listener = new HospitalListener();
        DatabaseReference captain = fireDb.getReference().child("Hospitais");
        captain.addValueEventListener(listener);
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
