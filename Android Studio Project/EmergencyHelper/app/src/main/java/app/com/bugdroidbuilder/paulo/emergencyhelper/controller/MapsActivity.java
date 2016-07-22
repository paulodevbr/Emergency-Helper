package app.com.bugdroidbuilder.paulo.emergencyhelper.controller;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import app.com.bugdroidbuilder.paulo.emergencyhelper.R;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Point;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.User;
import app.com.bugdroidbuilder.paulo.emergencyhelper.service.LocationService;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;
import app.com.bugdroidbuilder.paulo.emergencyhelper.service.ServicesVerification;
import app.com.bugdroidbuilder.paulo.emergencyhelper.components.ToolbarSupport;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

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
    private User user = null;
    private LocationService locationService;
    private boolean closeLocation = false;
    private boolean closeDatabase = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        // Iniciando serviços
        ButterKnife.bind(this);
        this.locationService = new LocationService(this);
        FirebaseDatabase.getInstance().getReference().keepSynced(true);
        //---------------------

        // Requisitando permição de rede
        permissionHandler.requestPermissionNetworkState(this);

        // Verificando se a rede está disponivel
        if(ServicesVerification.isOnline(this)) {

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

            final Toolbar toolbar = (Toolbar) findViewById(R.id.maps_toolbar);
            ToolbarSupport.startToolbar(this, toolbar, "Emergency Helper");

            this.loadButton();

            // Iniciando conexão com o serviço de localização (GPS)
            this.locationService.connect();

            this.startAsyncTask();

            // Iniciando mapa
            mapFragment.getMapAsync(this);

        }else{

            // Exibir lista
        }
    }

    private void startAsyncTask(){

        // Iniciando asyncTask
        AsyncHospital asyncHospital = new AsyncHospital();
        asyncHospital.execute();
    }

    private void loadButton(){

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

        fabNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navegar();
            }
        });
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

    // Mapa disponível para uso
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setMapToolbarEnabled(false);
    }

    public void navegar(){
        /*hideButtons();
        Intent intent = new Intent(this, NavigateActivity.class);
        Gson gson = new Gson();
        String json = gson.toJson(setHospital);

        intent.putExtra("hospitais",json);
        startActivity(intent);*/

        List<Point> hospitalPointList = new ArrayList<>();
        for(Hospital hospital : this.setHospital){
            hospitalPointList.add(hospital);
        }

        if(this.user != null) {
            hospitalPointList = PointController.orderByReference(this.user, hospitalPointList);
        }
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

    public void hideButtons() {
        fabCall.hide();
        fabNavigate.hide();
    }

    public void showButtons() {
        fabNavigate.show();
        fabCall.show();
    }

    // Armazenando lista de hospitais e inserindo marcadores no mapa
    @Subscribe
    public void onEvent(Set<Hospital> setHospital){

        this.setHospital = setHospital;

        for(Hospital hospital : setHospital) {
            MarkerOptions marker = hospital.drawPoint(this);
            mMap.addMarker(marker);
        }

        // Definindo clickListener para os marcadores
        HospitalMarkerClickListener hospitalMarkerClickListener = new HospitalMarkerClickListener(setHospital, this);
        mMap.setOnMarkerClickListener(hospitalMarkerClickListener);

        this.closeDatabase = true;
    }

    // Buscando localização do usuario (GPS)
    @Subscribe
    public void onEvent(Location myLocation){

        // Verificando se o mapa/localização existe
        if((myLocation != null) && (this.mMap != null)){

            // Verificando se o GPS está habilitado
            // Se estiver insere o marcador do usuario
            if(ServicesVerification.isGpsEnable(this)) {
                this.user = new User(myLocation);
                LatLng latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());

                int zoomInicial = 12;

                MarkerOptions userMarker = this.user.drawPoint(this);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomInicial), 1500, null);
                mMap.addMarker(userMarker);
            }

            // Desconectando serviço de localização
            this.locationService.disconnect();
            this.closeLocation = true;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        this.locationService.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Verificando se a localização já foi encerrada definitivamente
        // Caso não tenha sido, reabrir conexão.
        if(!this.closeLocation){
            this.locationService.connect();
        }

        if(!this.closeDatabase){
            this.startAsyncTask();
        }
        showButtons();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        this.locationService.disconnect();
        super.onStop();
    }

    @Override
    public void onDestroy(){
        this.locationService.disconnect();
        super.onDestroy();
    }
}
