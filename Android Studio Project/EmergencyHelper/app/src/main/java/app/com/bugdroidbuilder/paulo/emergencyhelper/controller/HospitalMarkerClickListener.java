package app.com.bugdroidbuilder.paulo.emergencyhelper.controller;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import org.greenrobot.eventbus.EventBus;

import java.util.Set;

import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;

/**
 * Created by pedro on 09/07/16.
 */
public class HospitalMarkerClickListener implements GoogleMap.OnMarkerClickListener {

    private Set<Hospital> hospitalSet;
    private Context context;

    public HospitalMarkerClickListener(Set<Hospital> hospitalSet, Context context) {
        this.hospitalSet = hospitalSet;
        this.context = context;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Hospital idHospital;

        for(Hospital hospital : hospitalSet){

            boolean lat = (hospital.getLatitude() == marker.getPosition().latitude);
            boolean lng = (hospital.getLongitude() == marker.getPosition().longitude);

            if(lat && lng){
                idHospital = hospital;
                viewInformation(idHospital);
                break;
            }
        }

        return false;
    }

    private void viewInformation(Hospital idHospital){

        Intent intent = new Intent(context, HospitalDescriptionActivity.class);
        EventBus.getDefault().postSticky(idHospital);

        context.startActivity(intent);

    }
}
