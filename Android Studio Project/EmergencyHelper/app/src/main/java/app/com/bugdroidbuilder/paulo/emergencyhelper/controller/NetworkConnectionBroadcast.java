package app.com.bugdroidbuilder.paulo.emergencyhelper.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import app.com.bugdroidbuilder.paulo.emergencyhelper.model.BroadcastResponse;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.GlobalValues;

/**
 * Created by pedro on 23/07/16.
 */
public class NetworkConnectionBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = (activeNetwork != null) && activeNetwork.isConnected();
        boolean currentState = GlobalValues.getInstance().isOnline();

        if(!isConnected){
            Toast toast = Toast.makeText(context, "Modo offline iniciado", Toast.LENGTH_LONG);
            toast.show();
        }else{
            AsyncHospital asyncHospital = new AsyncHospital();
            asyncHospital.execute();
        }

        if(currentState != isConnected) {
            GlobalValues.getInstance().setInternetCurrentState(isConnected);
            BroadcastResponse response = new BroadcastResponse(isConnected, BroadcastResponse.FLAG_INTERNET);
            EventBus.getDefault().post(response);
        }
    }
}
