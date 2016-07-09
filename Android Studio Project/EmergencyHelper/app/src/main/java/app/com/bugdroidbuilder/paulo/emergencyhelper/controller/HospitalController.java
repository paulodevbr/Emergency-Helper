package app.com.bugdroidbuilder.paulo.emergencyhelper.controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.TelefoneUtil;

/**
 * Created by pedro on 26/06/16.
 */
public class HospitalController {

    public static void callHospital(Hospital hospital, Context context) {

        String uri = "tel:" + hospital.getTelefone().trim() ;
        final Intent intent;

        if(PermissionHandler.permissionCall){
            intent = new Intent(Intent.ACTION_CALL);

        }else{
            intent = new Intent(Intent.ACTION_DIAL);
        }

        intent.setData(Uri.parse(uri));
        context.startActivity(intent);
    }

    public static MarkerOptions getHospitalMark(Hospital hospital){

        float iconTone;
        switch(hospital.getNota()){
            case 0:
                iconTone = BitmapDescriptorFactory.HUE_RED;
                break;

            case 1:
                iconTone = BitmapDescriptorFactory.HUE_VIOLET;
                break;

            case 2:
                iconTone = BitmapDescriptorFactory.HUE_YELLOW;
                break;

            case 3:
                iconTone = BitmapDescriptorFactory.HUE_ORANGE;
                break;

            case 4:
                iconTone = BitmapDescriptorFactory.HUE_MAGENTA;
                break;

            case 5:
                iconTone = BitmapDescriptorFactory.HUE_GREEN;
                break;

            default:
                iconTone = BitmapDescriptorFactory.HUE_AZURE;
        }

        LatLng position = new LatLng(hospital.getLatitude(), hospital.getLongitude());
        MarkerOptions mark = new MarkerOptions()
                .position(position)
                .title(hospital.getNome())
                .snippet(hospital.getEndereco() + "\nTel: " + hospital.getTelefone())
                .icon(BitmapDescriptorFactory.fromFile("iconGrade_" + hospital.getNota()));

        return(mark);
    }

}
