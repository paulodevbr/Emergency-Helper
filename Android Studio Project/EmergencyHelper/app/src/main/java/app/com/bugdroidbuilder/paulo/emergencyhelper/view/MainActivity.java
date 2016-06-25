package app.com.bugdroidbuilder.paulo.emergencyhelper.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import app.com.bugdroidbuilder.paulo.emergencyhelper.R;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controler.FireControler;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;

public class MainActivity extends AppCompatActivity {

    private FireControler fireControler = new FireControler();
    private Hospital hospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       hospital = fireControler.getHospital("Hosp");

        //TextView tv = (TextView) findViewById(R.id.hospitalName);
        //tv.setText(hospital.getNome());
    }

    public void callHosp(View view){
        fireControler.callHospital(hospital,this);
    }

    public void goMap(View view){
        Intent myIntent = new Intent(this, MapsActivity.class);
        this.startActivity(myIntent);
    }
}
