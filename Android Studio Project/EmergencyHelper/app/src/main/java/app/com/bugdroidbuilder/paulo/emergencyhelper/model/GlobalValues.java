package app.com.bugdroidbuilder.paulo.emergencyhelper.model;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.firebase.database.FirebaseDatabase;

import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.AsyncHospital;
import app.com.bugdroidbuilder.paulo.emergencyhelper.service.LocationService;

/**
 * Created by pedro on 23/07/16.
 */
public class GlobalValues {
    private static GlobalValues ourInstance = new GlobalValues();
    private boolean internetCurrentState = true;
    private boolean gpsCurrentState = false;
    private boolean firstStart = true;

    private LocationService locationService;

    public static GlobalValues getInstance() {
        return ourInstance;
    }

    private GlobalValues() {
    }

    public boolean isOnline() {
        return internetCurrentState;
    }

    public boolean haveLocation() {
        return gpsCurrentState;
    }

    public void setInternetCurrentState(boolean internetCurrentState) {
        this.internetCurrentState = internetCurrentState;
    }

    public void setGpsCurrentState(boolean gpsCurrentState) {
        this.gpsCurrentState = gpsCurrentState;
    }

    public void firstStart(Context context){

        if(firstStart) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = (activeNetwork != null) && activeNetwork.isConnected();
            this.internetCurrentState = isConnected;

            LocationManager mlocManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);;
            boolean haveLocation = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            this.gpsCurrentState = haveLocation;

            FirebaseDatabase.getInstance().setPersistenceEnabled(true);

            this.locationService = new LocationService(context);

            // Iniciando asyncTask
            AsyncHospital asyncHospital = new AsyncHospital();
            asyncHospital.execute();
            firstStart = false;
        }

    }

    public void connect() {
        if(this.gpsCurrentState) {
            this.locationService.connect();
        }
    }

    public void disconnect() {
        if(this.gpsCurrentState) {
            this.locationService.disconnect();
        }
    }
}
