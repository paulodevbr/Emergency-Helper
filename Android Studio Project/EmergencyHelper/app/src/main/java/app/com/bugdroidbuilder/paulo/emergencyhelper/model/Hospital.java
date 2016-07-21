package app.com.bugdroidbuilder.paulo.emergencyhelper.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.PointController;

/**
 * Created by pedro on 17/06/16.
 */
public class Hospital implements Point{

    private String storageId;
    private String tipo;

    private double latitude;
    private double longitude;

    private String nome;
    private String telefone;
    private String endereco;
    private String descricao;

    private Map<String,String> fotos;
    private int nota;

    public Hospital() {
    }

    public String getStorageId() {
        return this.storageId;
    }

    public String getTipo() {
        return this.tipo;
    }

    @Override
    public double getLatitude() {
        return this.latitude;
    }

    @Override
    public double getLongitude() {
        return this.longitude;
    }

    public String getNome() {
        return this.nome;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Map<String, String> getFotos() {
        return this.fotos;
    }

    public int getNota() {
        return this.nota;
    }

    @Override
    public MarkerOptions drawPoint(Context context) {

        Bitmap bitmap = PointController.createBitmapHospitalIcon(context);
        LatLng position = new LatLng(this.getLatitude(), this.getLongitude());
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(bitmap);
        MarkerOptions mark = new MarkerOptions()
                .position(position)
                .title(this.nome)
                .snippet(this.endereco + "\nTel: " + this.telefone)
                .icon(icon);

        return(mark);
    }
}
