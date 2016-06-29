package app.com.bugdroidbuilder.paulo.emergencyhelper.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.com.bugdroidbuilder.paulo.emergencyhelper.R;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.AsyncHospital;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.AsyncHospitalInterface;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;

public class MainActivity extends AppCompatActivity implements AsyncHospitalInterface{

    AsyncHospital asyncHospitalTask = new AsyncHospital();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> listaHosp = new ArrayList<>();
        listaHosp.add("Hosp1");

        this.asyncHospitalTask.delegate = this;
        this.asyncHospitalTask.execute(listaHosp);
    }

    @Override
    public void processFinish(List<Hospital> output) {

        TextView tv = (TextView) findViewById(R.id.hospitalName);
        Hospital hospital = output.get(0);
        tv.setText(hospital.getNome());


        //Bitmap bm = FirebaseController.getImageByHospital(hospital, "imagem1");
        //ImageView imv = (ImageView) findViewById(R.id.image);
        //imv.setImageBitmap(bm);
    }





    public void goMap(View view){
        Intent myIntent = new Intent(this, MapsActivity.class);
        this.startActivity(myIntent);
    }

}
