package app.com.bugdroidbuilder.paulo.emergencyhelper.model;

import android.content.Context;

import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by pedro on 21/07/16.
 */
public interface Point {
    public MarkerOptions drawPoint(Context context);
    public double getLatitude();
    public double getLongitude();
}
