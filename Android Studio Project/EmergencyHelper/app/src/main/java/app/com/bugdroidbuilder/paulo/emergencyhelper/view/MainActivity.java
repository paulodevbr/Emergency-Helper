package app.com.bugdroidbuilder.paulo.emergencyhelper.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Set;

import app.com.bugdroidbuilder.paulo.emergencyhelper.R;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.AsyncHospitalCollection;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.AsyncHospitalInterface;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.HospitalStorage;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;

public class MainActivity extends AppCompatActivity implements AsyncHospitalInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsyncHospitalCollection asyncHospitalCollection= new AsyncHospitalCollection();
        asyncHospitalCollection.setDelegate(this);
        asyncHospitalCollection.execute();
    }

    @Override
    public void processFinishHospital(Set<Hospital> output) {

        HospitalStorage.getInstance().setStorage(output);

        Intent myIntent = new Intent(this, MapsActivity.class);
        this.startActivity(myIntent);
    }
}