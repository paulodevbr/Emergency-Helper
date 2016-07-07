package app.com.bugdroidbuilder.paulo.emergencyhelper.controller;

import android.os.AsyncTask;

import java.util.HashSet;
import java.util.Set;

import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;

/**
 * Created by pedro on 26/06/16.
 */
public class AsyncHospitalSingle extends AsyncTask<Set<String>, Hospital, Void> {

    private Set<Hospital> hospitalList = new HashSet<>();
    public AsyncHospitalInterface delegate = null;

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
         this.hospitalList.add(values[0]);

        // Insira o código do mapa aqui.
    }

    @Override
    protected void onPostExecute(Void avoid) {
        delegate.processFinishHospital(this.hospitalList);
    }
}
