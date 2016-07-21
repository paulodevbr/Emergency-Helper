package app.com.bugdroidbuilder.paulo.emergencyhelper.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import app.com.bugdroidbuilder.paulo.emergencyhelper.R;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Point;

/**
 * Created by pedro on 26/06/16.
 */
public class PointController{

    private static Context context;

    public static Bitmap createBitmapHospitalIcon(Context context){

        Drawable circle = context.getResources().getDrawable(R.drawable.place_hospital);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(circle.getIntrinsicWidth(), circle.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        circle.setBounds(0, 0, circle.getIntrinsicWidth(), circle.getIntrinsicHeight());
        circle.draw(canvas);

        return(bitmap);
    }

    public static Bitmap createBitmapUserIcon(Context context){

        Drawable circle = context.getResources().getDrawable(R.drawable.user_position);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(circle.getIntrinsicWidth(), circle.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        circle.setBounds(0, 0, circle.getIntrinsicWidth(), circle.getIntrinsicHeight());
        circle.draw(canvas);

        return(bitmap);
    }

    public static List<Point> orderByReference(final Point reference, List<Point> pointList){

        Comparator comp = new Comparator<Point>() {
            @Override
            public int compare(Point o, Point o2) {
                float[] result1 = new float[3];
                Location.distanceBetween(reference.getLatitude(), reference.getLongitude(), o.getLatitude(), o.getLongitude(), result1);
                Float distance1 = result1[0];

                float[] result2 = new float[3];
                Location.distanceBetween(reference.getLatitude(), reference.getLongitude(), o2.getLatitude(), o2.getLongitude(), result2);
                Float distance2 = result2[0];

                return distance1.compareTo(distance2);
            }
        };

        Collections.sort(pointList, comp);
        return(pointList);
    }

}