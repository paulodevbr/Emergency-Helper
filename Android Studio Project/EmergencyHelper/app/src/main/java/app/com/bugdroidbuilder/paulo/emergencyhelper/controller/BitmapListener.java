package app.com.bugdroidbuilder.paulo.emergencyhelper.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Created by pedro on 26/06/16.
 */
public class BitmapListener implements OnSuccessListener<byte[]>, OnFailureListener {

    private Bitmap instance = null;
    private boolean getData = false;

    @Override
    public void onSuccess(byte[] bytes) {
        this.instance = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        this.getData = true;
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        this.getData = false;
    }

    public boolean isGetData() {
        return getData;
    }

    public Bitmap getInstance() {
        return instance;
    }

}
