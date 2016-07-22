package app.com.bugdroidbuilder.paulo.emergencyhelper.controller;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import app.com.bugdroidbuilder.paulo.emergencyhelper.R;

/**
 * Created by paulo on 22/07/16.
 */
public class NavigationHandler {

    public static void navegar(final Activity activity,
                               final double latitude,
                               final double longitude) {

        String tagEndereco = activity.getResources().getString(R.string.endereco_maps);
        String linkMaps = activity.getResources().getString(R.string.link_maps);
        String pacoteMaps = activity.getResources().getString(R.string.pacote_maps);
        // Create a Uri from an intent string. Use the result to create an Intent.
        StringBuilder endereco = new StringBuilder().append(tagEndereco)
                .append(latitude)
                .append(",")
                .append(longitude);

        StringBuilder maps = new StringBuilder().append(linkMaps).append(endereco.toString());
        Uri gmmIntentUri = Uri.parse(maps.toString());


        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

        mapIntent.setPackage(pacoteMaps);

        activity.startActivity(mapIntent);
    }
}
