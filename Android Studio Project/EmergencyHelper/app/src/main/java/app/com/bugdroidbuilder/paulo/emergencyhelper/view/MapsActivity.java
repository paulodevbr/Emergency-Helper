package app.com.bugdroidbuilder.paulo.emergencyhelper.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.Set;

import app.com.bugdroidbuilder.paulo.emergencyhelper.R;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.AsyncHospitalCollection;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.AsyncHospitalInterface;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.HospitalController;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.HospitalMarkerClickListener;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.PermissionHandler;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.TelefoneHandler;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, AsyncHospitalInterface {
    private final PermissionHandler permissionHandler = new PermissionHandler();
    @Bind(R.id.fab_call)
    FloatingActionButton fabCall;
    @Bind(R.id.fab_navigate)
    FloatingActionButton fabNavigate;
    @Bind(R.id.fab_cancel_maps)
    FloatingActionButton fabCancel;
    private Activity activity = this;
    private GoogleMap mMap;
    private MapFragment mapFragment;
    private Set<Hospital> setHospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);


        permissionHandler.requestPermissionNetworkState(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.maps_toolbar);
        ToolbarSupport.startToolbar(this, toolbar,"Emergency Helper");

        fabCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chamarAjuda();
            }
        });

        fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelarLigacao();
            }
        });

//        fabNavigate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navegar();
//            }
//        });


        AsyncHospitalCollection asyncHospitalCollection = new AsyncHospitalCollection(this);
        asyncHospitalCollection.execute();
        activity = this;
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
        hideButtons();
        if (id == R.id.menu_telefones_uteis) {
            startActivity(new Intent(this, TelefonesUteisActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        showButtons();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        permissionHandler.requestPermissionLocation(this);

        int zoomInicial = 12;
        mMap = googleMap;

        LatLng mylatlng = new LatLng(-16.6871724, -49.257001);

        for (Hospital hospital : setHospital) {
            mMap.addMarker(HospitalController.getHospitalMark(this, hospital));
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylatlng, zoomInicial), 1500, null);

        HospitalMarkerClickListener hospitalMarkerClickListener = new HospitalMarkerClickListener(setHospital, this);
        mMap.setOnMarkerClickListener(hospitalMarkerClickListener);

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setMapToolbarEnabled(false);
    }

    public void navegar(){
        hideButtons();
        Intent intent = new Intent(this, NavigateActivity.class);
        Gson gson = new Gson();
        String json = gson.toJson(setHospital);

        intent.putExtra("hospitais",json);
        startActivity(intent);
    }

    public void chamarAjuda() {

        hideButtons();

        permissionHandler.requestPermissionCall(this);

        String numeroEmergencia = "192";

        TelefoneHandler.ligarEmergencia(activity,
                numeroEmergencia,
                R.id.popup_ligacao,
                R.id.fab_cancel_maps,
                R.id.text_count_down_maps);


    }

    public void cancelarLigacao() {
        showButtons();
        TelefoneHandler.cancelarLigacao();
    }

    @Override
    public void processFinishHospital(Set<Hospital> output) {

        this.setHospital = output;
        mapFragment.getMapAsync(this);
    }

    public void hideButtons() {
        fabCall.hide();
        fabNavigate.hide();
    }

    public void showButtons() {
        fabNavigate.show();
        fabCall.show();
    }
}
