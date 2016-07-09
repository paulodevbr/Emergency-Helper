package app.com.bugdroidbuilder.paulo.emergencyhelper.view;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.util.Set;

import app.com.bugdroidbuilder.paulo.emergencyhelper.R;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.HospitalController;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.HospitalStorage;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.PermissionHandler;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private PermissionHandler permissionHandler = new PermissionHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        permissionHandler.requestPermissionCall(this);
        permissionHandler.requestPermissionLocation(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.maps_toolbar);
        ToolbarSupport.startToolbar(this, toolbar, "Emergency Helper");

        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_maps, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.menu_telefones_uteis) {
            startActivity(new Intent(this, TelefonesUteisActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        Location myLocation = mMap.getMyLocation();
        LatLng mylatlng = new LatLng(myLocation.getLatitude(),myLocation.getLongitude());

        Set<Hospital> hospitalSet = HospitalStorage.getInstance().getStorage();
        for(Hospital hospital : hospitalSet){
            mMap.addMarker(HospitalController.getHospitalMark(hospital));
        }

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylatlng, 15), 1500, null);

    }

    public void callHosp(View view){

        String numeroEmergencia = "192";
        String uri = "tel:" + numeroEmergencia.trim() ;
        final Intent intent;

        if(PermissionHandler.permissionCall){
            intent = new Intent(Intent.ACTION_CALL);

        }else{
            intent = new Intent(Intent.ACTION_DIAL);
        }
        intent.setData(Uri.parse(uri));

        new CountDownTimer(3200, 1000) {

            public void onTick(long millisUntilFinished) {

                Toast.makeText(getApplicationContext(), "Ligando em 3 segundos", Toast.LENGTH_SHORT).show();
            }

            public void onFinish() {
                startActivity(intent);
            }

        }.start();


    }
}
