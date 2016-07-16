package app.com.bugdroidbuilder.paulo.emergencyhelper.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Set;

import app.com.bugdroidbuilder.paulo.emergencyhelper.R;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.AsyncHospital;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.HospitalController;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.HospitalMarkerClickListener;
import app.com.bugdroidbuilder.paulo.emergencyhelper.service.PermissionHandler;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private PermissionHandler permissionHandler = new PermissionHandler();
    private MapFragment mapFragment;
    private Set<Hospital> setHospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        permissionHandler.requestPermissionCall(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        Toolbar toolbar = (Toolbar) findViewById(R.id.maps_toolbar);
        ToolbarSupport.startToolbar(this, toolbar, "Emergency Helper");

        AsyncHospital asyncHospital = new AsyncHospital();
        asyncHospital.execute();

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
        permissionHandler.requestPermissionLocation(this);

        int zoomInicial = 12;
        mMap = googleMap;

        LatLng mylatlng = new LatLng(-16.6871724,-49.257001);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylatlng, zoomInicial), 1500, null);
    }

    public void emergencia(View view) {

        String numeroEmergencia = "192";
        String uri = "tel:" + numeroEmergencia.trim();
        final Intent intent;

        if (PermissionHandler.permissionCall) {
            intent = new Intent(Intent.ACTION_CALL);

        } else {
            intent = new Intent(Intent.ACTION_DIAL);
        }
        intent.setData(Uri.parse(uri));

       TelefoneHandler.ligarEmergencia(this,intent, R.id.fab_cancel_maps, R.id.text_count_down_maps);


    }
    public void cancelarLigacao(View view){

        TelefoneHandler.cancelarLigacao();
    }

    private Bitmap createBitmapIcon(){

        Drawable circle = this.getResources().getDrawable(R.drawable.place_hospital);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(circle.getIntrinsicWidth(), circle.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        circle.setBounds(0, 0, circle.getIntrinsicWidth(), circle.getIntrinsicHeight());
        circle.draw(canvas);

        return(bitmap);
    }

    @Subscribe
    public void onEvent(Set<Hospital> setHospital){

        this.setHospital = setHospital;

        HospitalController hospitalController = new HospitalController(this.createBitmapIcon());

        for(Hospital hospital : setHospital) {
            MarkerOptions marker = hospitalController.getHospitalMark(hospital);
            mMap.addMarker(marker);
        }

        HospitalMarkerClickListener hospitalMarkerClickListener = new HospitalMarkerClickListener(setHospital, this);
        mMap.setOnMarkerClickListener(hospitalMarkerClickListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
