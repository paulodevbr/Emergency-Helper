package app.com.bugdroidbuilder.paulo.emergencyhelper.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pedro on 17/07/16.
 */
public class CreateHospitalDatabase extends SQLiteOpenHelper{

    public static final String NOMEBANCO = "hospital.db";
    public static final int VERSAO = 1;
    public static final String TABELA = "hospital";

    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String NOME = "nome";
    public static final String DESCRICAO = "descricao";
    public static final String ENDERECO = "endereco";
    public static final String TELEFONE = "telefone";
    public static final String TIPO = "tipo";
    public static final String STORAGEID = "storageid";
    public static final String NOTA = "nota";
    public static final String FOTOS = "fotos";

    public CreateHospitalDatabase(Context context){
        super(context, NOMEBANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE ");
        builder.append(TABELA).append(" ( ");
        builder.append(LATITUDE).append(" DECIMAL(6,5) ").append(",");
        builder.append(LONGITUDE).append(" DECIMAL(6,5) ").append(",");
        builder.append(NOME).append(" TEXT ").append(",");
        builder.append(DESCRICAO).append(" TEXT ").append(",");
        builder.append(ENDERECO).append(" TEXT ").append(",");
        builder.append(TELEFONE).append(" TEXT ").append(",");
        builder.append(ENDERECO).append(" TEXT ").append(",");
        builder.append(TIPO).append(" TEXT ").append(",");
        builder.append(STORAGEID).append(" TEXT ").append(",");
        builder.append(NOTA).append(" INTEGER ").append(",");
        builder.append(FOTOS).append(" TEXT ").append(")");

        db.execSQL(builder.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        StringBuilder builder = new StringBuilder();
        builder.append("DROP TABLE IF EXISTS ");
        builder.append(TABELA);

        db.execSQL(builder.toString());
        onCreate(db);
    }
}
