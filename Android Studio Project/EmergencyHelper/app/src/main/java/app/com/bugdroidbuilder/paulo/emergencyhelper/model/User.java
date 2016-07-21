package app.com.bugdroidbuilder.paulo.emergencyhelper.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.PointController;

/**
 * Created by pedro on 21/07/16.
 */
public class User implements Point{

    Location location;

    public User(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public MarkerOptions drawPoint(Context context) {
        Bitmap bitmap = PointController.createBitmapUserIcon(context);
        LatLng position = new LatLng(this.location.getLatitude(), this.location.getLongitude());
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(bitmap);
        MarkerOptions mark = new MarkerOptions()
                .position(position)
                .icon(icon);

        return(mark);
    }

    @Override
    public double getLatitude() {
        return this.location.getLatitude();
    }

    @Override
    public double getLongitude() {
        return this.location.getLongitude();
    }
}
