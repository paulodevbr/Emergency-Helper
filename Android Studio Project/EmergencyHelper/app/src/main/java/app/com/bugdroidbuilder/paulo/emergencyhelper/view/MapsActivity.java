package app.com.bugdroidbuilder.paulo.emergencyhelper.view;

import android.app.Activity;
import android.content.Intent;
<<<<<<< HEAD
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
=======
>>>>>>> master
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
<<<<<<< HEAD
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
=======
import com.google.gson.Gson;
>>>>>>> master

import java.util.Set;

import app.com.bugdroidbuilder.paulo.emergencyhelper.R;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.AsyncHospital;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.HospitalController;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.HospitalDAO;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.HospitalMarkerClickListener;
<<<<<<< HEAD
import app.com.bugdroidbuilder.paulo.emergencyhelper.service.PermissionHandler;
=======
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.PermissionHandler;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.TelefoneHandler;
>>>>>>> master
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;
import butterknife.Bind;
import butterknife.ButterKnife;

<<<<<<< HEAD
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
=======
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, AsyncHospitalInterface {
    private final PermissionHandler permissionHandler = new PermissionHandler();
    @Bind(R.id.fab_call)
    FloatingActionButton fabCall;
    @Bind(R.id.fab_navigate)
    FloatingActionButton fabNavigate;
    @Bind(R.id.fab_cancel_maps)
    FloatingActionButton fabCancel;
    private Activity activity = this;
>>>>>>> master
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

<<<<<<< HEAD
        Toolbar toolbar = (Toolbar) findViewById(R.id.maps_toolbar);
        ToolbarSupport.startToolbar(this, toolbar, "Emergency Helper");
=======
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

>>>>>>> master

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

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylatlng, zoomInicial), 1500, null);
<<<<<<< HEAD
=======

        HospitalMarkerClickListener hospitalMarkerClickListener = new HospitalMarkerClickListener(setHospital, this);
        mMap.setOnMarkerClickListener(hospitalMarkerClickListener);

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setMapToolbarEnabled(false);
>>>>>>> master
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

<<<<<<< HEAD
       TelefoneHandler.ligarEmergencia(this,intent, R.id.fab_cancel_maps, R.id.text_count_down_maps);
=======
        TelefoneHandler.ligarEmergencia(activity,
                numeroEmergencia,
                R.id.popup_ligacao,
                R.id.fab_cancel_maps,
                R.id.text_count_down_maps);
>>>>>>> master


    }

    public void cancelarLigacao() {
        showButtons();
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

    public void hideButtons() {
        fabCall.hide();
        fabNavigate.hide();
    }

    public void showButtons() {
        fabNavigate.show();
        fabCall.show();
    }
}
