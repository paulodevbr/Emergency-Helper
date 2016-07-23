package app.com.bugdroidbuilder.paulo.emergencyhelper.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import org.greenrobot.eventbus.EventBus;

import app.com.bugdroidbuilder.paulo.emergencyhelper.model.BroadcastResponse;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.GlobalValues;

/**
 * Created by pedro on 23/07/16.
 */
public class LocationConnectionBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        LocationManager mlocManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);;
        boolean haveLocation = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean currentState = GlobalValues.getInstance().haveLocation();

        if(currentState != haveLocation) {
            GlobalValues.getInstance().setGpsCurrentState(haveLocation);
            BroadcastResponse response = new BroadcastResponse(haveLocation, BroadcastResponse.FLAG_GPS);
            EventBus.getDefault().post(response);
        }
    }
}
