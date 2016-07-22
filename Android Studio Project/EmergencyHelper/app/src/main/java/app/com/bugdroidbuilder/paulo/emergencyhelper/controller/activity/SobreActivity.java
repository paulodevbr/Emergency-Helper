package app.com.bugdroidbuilder.paulo.emergencyhelper.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import app.com.bugdroidbuilder.paulo.emergencyhelper.R;
import app.com.bugdroidbuilder.paulo.emergencyhelper.components.ToolbarSupport;
import butterknife.Bind;
import butterknife.ButterKnife;

public class SobreActivity extends AppCompatActivity {

    @Bind(R.id.sobre_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        ButterKnife.bind(this);

        ToolbarSupport.startToolbarWithArrow(this, toolbar,"Sobre");


    }
}
