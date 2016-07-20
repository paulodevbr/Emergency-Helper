package app.com.bugdroidbuilder.paulo.emergencyhelper.controller;

import android.os.AsyncTask;

import app.com.bugdroidbuilder.paulo.emergencyhelper.service.FirebaseController;

/**
 * Created by pedro on 06/07/16.
 */
public class AsyncHospital extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {

        FirebaseController.eventGetHospital();
        return(null);
    }

}
