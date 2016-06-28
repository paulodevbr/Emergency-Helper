package app.com.bugdroidbuilder.paulo.emergencyhelper.controler;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;

/**
 * Created by pedro on 26/06/16.
 */
public class AsyncHospital extends AsyncTask< List<String>, Hospital, Void> {

    private List<Hospital> hospitalList = new ArrayList<>();
    public AsyncHospitalInterface delegate = null;

    @Override
    protected Void doInBackground(List<String>... params) {

        List<String> listaId = params[0];

        for(String id : listaId){

            Hospital hosp = FireBaseControler.getHospital(id);
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
        delegate.processFinish(this.hospitalList);
    }
}
