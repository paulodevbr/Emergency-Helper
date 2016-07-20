package app.com.bugdroidbuilder.paulo.emergencyhelper.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.HospitalDAOException;

/**
 * Created by pedro on 17/07/16.
 */
public class HospitalDAO {

    private CreateHospitalDatabase banco;

    public HospitalDAO(Context context){
        this.banco = new CreateHospitalDatabase(context);
    }

    public void insert(Hospital hospital){
        ContentValues valores = new ContentValues();
        SQLiteDatabase db = this.banco.getWritableDatabase();
        Gson gson = new Gson();

        valores.put(CreateHospitalDatabase.LATITUDE, hospital.getLatitude());
        valores.put(CreateHospitalDatabase.LONGITUDE, hospital.getLongitude());
        valores.put(CreateHospitalDatabase.NOME, hospital.getNome());
        valores.put(CreateHospitalDatabase.DESCRICAO, hospital.getDescricao());
        valores.put(CreateHospitalDatabase.ENDERECO, hospital.getEndereco());
        valores.put(CreateHospitalDatabase.TELEFONE, hospital.getTelefone());
        valores.put(CreateHospitalDatabase.TIPO, hospital.getTipo());
        valores.put(CreateHospitalDatabase.STORAGEID, hospital.getStorageId());
        valores.put(CreateHospitalDatabase.NOTA, hospital.getNota());
        valores.put(CreateHospitalDatabase.FOTOS, gson.toJson(hospital.getFotos()));

        long status = db.insert(CreateHospitalDatabase.TABELA, null, valores);
        db.close();

        if(status ==  -1){
            throw new HospitalDAOException("@string/dataBaseInsertError");
        }
    }

    public Set<Hospital> getAllHospital(){

        SQLiteDatabase db = this.banco.getReadableDatabase();

        List<String> lista = new ArrayList<>();
        lista.add(CreateHospitalDatabase.NOME);
        lista.add(CreateHospitalDatabase.DESCRICAO);
        lista.add(CreateHospitalDatabase.LATITUDE);
        lista.add(CreateHospitalDatabase.LONGITUDE);
        lista.add(CreateHospitalDatabase.ENDERECO);
        lista.add(CreateHospitalDatabase.TELEFONE);
        lista.add(CreateHospitalDatabase.FOTOS);
        lista.add(CreateHospitalDatabase.TIPO);
        lista.add(CreateHospitalDatabase.STORAGEID);

        String[] campos =  (String[]) lista.toArray();
        Cursor cursor = db.query(banco.TABELA, campos, null, null, null, null, null, null);
        db.close();

        if(cursor != null) {
            cursor.moveToFirst();
        }

        Set<Hospital> hospitalSet = new HashSet<>();
        return hospitalSet;
    }

}
