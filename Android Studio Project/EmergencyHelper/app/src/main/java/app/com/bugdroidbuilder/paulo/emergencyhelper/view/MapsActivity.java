package app.com.bugdroidbuilder.paulo.emergencyhelper.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import app.com.bugdroidbuilder.paulo.emergencyhelper.R;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

//        startToolbar();
        mapFragment.getMapAsync(this);
    }

    /** Initialize app toolbar
     *
     */
//    public void startToolbar(){
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_maps);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle("Emergency Helper");
//        toolbar.setTitleTextColor(getResources().getColor(R.color.common_google_signin_btn_text_dark));
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_maps, menu);
        return true;
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

        // Add a marker in INF and move the camera
        LatLng inf = new LatLng(-12.000, 25.555);
        MarkerOptions mark = new MarkerOptions().position(inf).title("teste");

        mMap.addMarker(mark);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(inf));

    }
}
