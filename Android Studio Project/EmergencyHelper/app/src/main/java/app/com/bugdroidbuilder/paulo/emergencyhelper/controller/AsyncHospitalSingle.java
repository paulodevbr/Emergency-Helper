package app.com.bugdroidbuilder.paulo.emergencyhelper.controller;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashSet;
import java.util.Set;

import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;

/**
 * Created by pedro on 26/06/16.
 */
public class AsyncHospitalSingle extends AsyncTask<Set<String>, Hospital, Void> {

    private Set<Hospital> hospitalList = new HashSet<>();
    private AsyncHospitalInterface delegate;

    public AsyncHospitalSingle(){

    }

    public AsyncHospitalSingle(AsyncHospitalInterface delegate){
        this.delegate = delegate;
    }

    public void setDelegate(AsyncHospitalInterface delegate) {
        this.delegate = delegate;
    }

    @Override
    protected Void doInBackground(Set<String>... params) {

        Set<String> listaId = params[0];

        for(String id : listaId){

            Hospital hosp = FirebaseController.getHospital(id);
            publishProgress(hosp);
        }

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
