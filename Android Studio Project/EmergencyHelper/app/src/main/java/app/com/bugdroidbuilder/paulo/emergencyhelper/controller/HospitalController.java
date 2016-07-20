package app.com.bugdroidbuilder.paulo.emergencyhelper.controller;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;

/**
 * Created by pedro on 26/06/16.
 */
public class HospitalController {

    private Bitmap bitmap;

    public HospitalController(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public MarkerOptions getHospitalMark(Hospital hospital){

        LatLng position = new LatLng(hospital.getLatitude(), hospital.getLongitude());
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(this.bitmap);
        MarkerOptions mark = new MarkerOptions()
                .position(position)
                .title(hospital.getNome())
                .snippet(hospital.getEndereco() + "\nTel: " + hospital.getTelefone())
                .icon(icon);

        return(mark);
    }

}