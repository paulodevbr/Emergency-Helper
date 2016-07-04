package app.com.bugdroidbuilder.paulo.emergencyhelper.controller;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by paulo on 03/07/16.
 */
public class PermissionHandler implements ActivityCompat.OnRequestPermissionsResultCallback{

    // para ativar a ligação direta no aplicativo
    // descomente os usos da variável permissionCall

    public static boolean permissionCall = false;


    private final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 0;

    public void requestPermissionCall(Activity activity){
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.CALL_PHONE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);

                // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else{
        //DESCOMENTE AQUI
        //PermissionHandler.permissionCall = true;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //DESCOMENTE AQUI
                   // PermissionHandler.permissionCall = true;

                } else {
                    //DESCOMENTE AQUI
                    //PermissionHandler.permissionCall = false;
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
