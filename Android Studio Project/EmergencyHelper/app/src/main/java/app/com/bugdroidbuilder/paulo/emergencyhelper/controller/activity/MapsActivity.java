package app.com.bugdroidbuilder.paulo.emergencyhelper.controller.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import app.com.bugdroidbuilder.paulo.emergencyhelper.R;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.HospitalMarkerClickListener;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.handler.PermissionHandler;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.PointController;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.handler.TelefoneHandler;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.BroadcastResponse;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.GlobalValues;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Point;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.User;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;
import app.com.bugdroidbuilder.paulo.emergencyhelper.components.ToolbarSupport;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback{
    public static List<Point> hospitais;
    private final PermissionHandler permissionHandler = new PermissionHandler();

    @Bind(R.id.refresh_button)
    ImageButton refreshButton;
    @Bind(R.id.maps_toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab_call)
    FloatingActionButton fabCall;
    @Bind(R.id.fab_navigate)
    FloatingActionButton fabNavigate;
    @Bind(R.id.fab_cancel_maps)
    FloatingActionButton fabCancel;

    private GoogleMap mMap;
    private MapFragment mapFragment;
    private Set<Hospital> setHospital;
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GlobalValues.getInstance().firstStart(this);

        if (GlobalValues.getInstance().isOnline()) {
            setTheme(R.style.AppTheme);
        }else {
            setTheme(R.style.AppThemeOffline);
        }

        setContentView(R.layout.activity_maps);

        startLayouts();
        startServices();
    }

    private void onRefresh() {
        startServices();
    }

    private void startLayouts(){
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        ButterKnife.bind(this);
        ToolbarSupport.startToolbar(this, toolbar, "Emergency Helper");
        this.loadButton();

    }

    private void startServices(){
        // Requisitando permissão de rede e localizacao
        permissionHandler.requestPermissionNetworkState(this);
        permissionHandler.requestPermissionLocation(this);

        // Verificando se a rede está disponivel
        if(GlobalValues.getInstance().isOnline()) {

            // Iniciando mapa
            mapFragment.getMapAsync(this);
        }else{

            //Intent intent = new Intent(this, TelefonesEmergenciaActivity.class);
            //intent.putExtra("online", false);
            //startActivity(intent);
        }


    }

    private void loadButton(){

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startServices();
            }
        });

        fabCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callHelp();
            }
        });

        fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelCall();
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
            Intent intent = new Intent(this, TelefonesEmergenciaActivity.class);
            startActivity(intent);
            return true;

        }else if(id == R.id.menu_sobre){
            startActivity(new Intent(this, SobreActivity.class));
            return true;
        } /*else if(id ==R.id.menu_configuracoes){
            startActivity(new Intent(this, ConfiguracoesActivity.class));
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    // Mapa disponível para uso
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(this.setHospital != null) {
            this.writeHospitalMarker();
        }

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setMapToolbarEnabled(false);
    }

    private void writeHospitalMarker(){

        mMap.clear();

        for (Hospital hospital : setHospital) {
            MarkerOptions marker = hospital.drawPoint(this);
            mMap.addMarker(marker);
        }

        // Definindo clickListener para os marcadores
        HospitalMarkerClickListener hospitalMarkerClickListener = new HospitalMarkerClickListener(setHospital, this);
        mMap.setOnMarkerClickListener(hospitalMarkerClickListener);
    }

    private void navegar(){

        List<Point> hospitalPointList = new ArrayList<>();
        for(Hospital hospital : this.setHospital){
            hospitalPointList.add(hospital);
        }

        if(this.user != null) {
            hospitalPointList = PointController.orderByReference(this.user, hospitalPointList);
            hospitais = hospitalPointList;
        }

        Intent intent = new Intent(this, NavigateActivity.class);
        startActivity(intent);
    }

    private void callHelp() {

        hideButtons();

        permissionHandler.requestPermissionCall(this);

        String numeroEmergencia = "192";

        TelefoneHandler.ligarEmergencia(this,
                numeroEmergencia,
                R.id.popup_ligacao,
                R.id.fab_cancel_maps,
                R.id.text_count_down_maps);
    }

    private void cancelCall() {
        showButtons();
        TelefoneHandler.cancelarLigacao();
    }

    private void hideButtons() {
        fabCall.hide();
        fabNavigate.hide();
    }

    private void showButtons() {
        fabNavigate.show();
        fabCall.show();
    }

    // Armazenando lista de hospitais e inserindo marcadores no mapa
    @Subscribe
    public void onEvent(Set<Hospital> setHospital){

        this.setHospital = setHospital;

        if(this.mMap != null) {
            this.writeHospitalMarker();
        }
    }

    // Buscando localização do usuario (GPS)
    @Subscribe
    public void onEvent(Location myLocation){

        // Verificando se o mapa/localização existe
        if((myLocation != null) && (this.mMap != null)){

            // Verificando se o GPS está habilitado
            // Se estiver insere o marcador do usuario
            if(GlobalValues.getInstance().haveLocation()) {

                this.user = new User(myLocation);
                LatLng latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());

                int zoomInicial = 12;

                MarkerOptions userMarker = this.user.drawPoint(this);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomInicial), 1500, null);
                mMap.addMarker(userMarker);
            }
        }
    }

    @Subscribe
    public void onEvent(BroadcastResponse response){

        switch(response.getFlag()) {
            case BroadcastResponse.FLAG_INTERNET:
                recreate();
                break;

            case BroadcastResponse.FLAG_GPS:
                GlobalValues.getInstance().connect();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        GlobalValues.getInstance().connect();
        showButtons();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        GlobalValues.getInstance().disconnect();
        super.onStop();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
