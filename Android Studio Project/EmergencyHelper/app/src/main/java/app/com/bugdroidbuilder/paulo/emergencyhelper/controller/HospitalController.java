package app.com.bugdroidbuilder.paulo.emergencyhelper.controller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import app.com.bugdroidbuilder.paulo.emergencyhelper.R;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;

/**
 * Created by pedro on 26/06/16.
 */
public class HospitalController {

    private Bitmap bitmap;

<<<<<<< HEAD
    public HospitalController(Bitmap bitmap){
        this.bitmap = bitmap;
    }
=======
    public static MarkerOptions getHospitalMark(Activity activity, Hospital hospital){

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


        Drawable marker = activity.getResources().getDrawable(R.drawable.place_hospital);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(marker.getIntrinsicWidth(),
                marker.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);

        canvas.setBitmap(bitmap);
        marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());
        marker.draw(canvas);
>>>>>>> master

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