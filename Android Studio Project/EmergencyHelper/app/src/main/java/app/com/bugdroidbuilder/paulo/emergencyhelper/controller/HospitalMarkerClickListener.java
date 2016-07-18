package app.com.bugdroidbuilder.paulo.emergencyhelper.controller;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.Set;

import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;
import app.com.bugdroidbuilder.paulo.emergencyhelper.view.HospitalDescriptionActivity;

/**
 * Created by pedro on 09/07/16.
 */
public class HospitalMarkerClickListener implements GoogleMap.OnMarkerClickListener {

    private Set<Hospital> hospitalSet;
    private Context context;

    public HospitalMarkerClickListener(Set<Hospital> hospitalSet, Context context) {
        this.hospitalSet = hospitalSet;
        this.context = context;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Hospital idHospital;

        for(Hospital hospital : hospitalSet){

            boolean lat = (hospital.getLatitude() == marker.getPosition().latitude);
            boolean lng = (hospital.getLongitude() == marker.getPosition().longitude);

            if(lat && lng){
                idHospital = hospital;
                viewInformation(idHospital);
                break;
            }
        }

        return false;
    }

    private void viewInformation(Hospital idHospital){

        String linkImagem = idHospital.getFotos().get("imagem1");
        String descricao = idHospital.getDescricao();
        String nome = idHospital.getNome();
        String endereco = idHospital.getEndereco();
        String telefone = idHospital.getTelefone();
        String tipo = idHospital.getTipo();
        Double latitude = idHospital.getLatitude();
        Double longitude = idHospital.getLongitude();


        Intent intent = new Intent(context, HospitalDescriptionActivity.class);
        intent.putExtra("imagem",linkImagem);
        intent.putExtra("descricao",descricao);
        intent.putExtra("nome",nome);
        intent.putExtra("endereco",endereco);
        intent.putExtra("telefone",telefone);
        intent.putExtra("tipo",tipo);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);

        context.startActivity(intent);

    }
}
