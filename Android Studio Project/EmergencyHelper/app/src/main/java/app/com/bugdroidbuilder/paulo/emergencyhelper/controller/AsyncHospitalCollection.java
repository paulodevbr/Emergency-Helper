package app.com.bugdroidbuilder.paulo.emergencyhelper.controller;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashSet;
import java.util.Set;

import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;

/**
 * Created by pedro on 06/07/16.
 */
public class AsyncHospitalCollection extends AsyncTask<Void, Hospital, Void> {

    private Set<Hospital> hospitalList = new HashSet<>();
    private AsyncHospitalInterface delegate;

    public AsyncHospitalCollection(){

    }

    public AsyncHospitalCollection(AsyncHospitalInterface delegate){
        this.delegate = delegate;
    }

    public void setDelegate(AsyncHospitalInterface delegate) {
        this.delegate = delegate;
    }

    @Override
    protected Void doInBackground(Void... params) {

        Set<Hospital> hospCollection = FirebaseController.getAllHospital();

        for(Hospital hosp : hospCollection) {
            publishProgress(hosp);
        }

        this.hospitalList = hospCollection;

        return(null);
    }

    @Override
    protected void onProgressUpdate(Hospital... values) {

        // Insira o código do mapa aqui.
    }

    @Override
    protected void onPostExecute(Void avoid) {
        delegate.processFinishHospital(this.hospitalList);
    }

}
