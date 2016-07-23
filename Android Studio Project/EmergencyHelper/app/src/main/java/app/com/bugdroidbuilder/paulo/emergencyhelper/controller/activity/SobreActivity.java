package app.com.bugdroidbuilder.paulo.emergencyhelper.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import app.com.bugdroidbuilder.paulo.emergencyhelper.R;
import app.com.bugdroidbuilder.paulo.emergencyhelper.components.ToolbarSupport;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.BroadcastResponse;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.GlobalValues;
import butterknife.Bind;
import butterknife.ButterKnife;

public class SobreActivity extends AppCompatActivity {

    @Bind(R.id.sobre_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (GlobalValues.getInstance().isOnline()) {
            setTheme(R.style.AppTheme);
        }else {
            setTheme(R.style.AppThemeOffline);
        }

        setContentView(R.layout.activity_sobre);

        ButterKnife.bind(this);

        ToolbarSupport.startToolbarWithArrow(this, toolbar,"Sobre");


    }

    @Subscribe
    public void onEvent(BroadcastResponse response){
        recreate();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
}
