package app.com.bugdroidbuilder.paulo.emergencyhelper.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

import app.com.bugdroidbuilder.paulo.emergencyhelper.R;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.AsyncHospitalInterface;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.AsyncHospitalSingle;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.AsyncHospitalCollection;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;

public class MainActivity extends AppCompatActivity implements AsyncHospitalInterface {

    AsyncHospitalCollection asyncHospitalCollection = new AsyncHospitalCollection();
    AsyncHospitalSingle asyncHospitalSingle = new AsyncHospitalSingle();

    Set<Hospital> hospitalSet = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this.asyncHospitalCollection.setDelegate(this);
        //this.asyncHospitalCollection.execute();

        Set<String> favoritos = new HashSet<>();
        favoritos.add("Hosp1");

        this.asyncHospitalSingle.delegate = this;
        this.asyncHospitalSingle.execute(favoritos);
    }

    @Override
    public void processFinishHospital(Set<Hospital> output) {

        this.hospitalSet = output;

        TextView tv = (TextView) findViewById(R.id.hospitalName);
        Hospital hospital = (Hospital) output.toArray()[0];
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
