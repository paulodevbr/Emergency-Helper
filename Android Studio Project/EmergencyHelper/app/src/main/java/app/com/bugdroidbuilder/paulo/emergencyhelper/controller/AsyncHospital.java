package app.com.bugdroidbuilder.paulo.emergencyhelper.controller;

import android.os.AsyncTask;

import app.com.bugdroidbuilder.paulo.emergencyhelper.service.FirebaseService;

/**
 * Created by pedro on 06/07/16.
 */
public class AsyncHospital extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {

        FirebaseService.eventGetHospital();
        return(null);
    }

}
