package app.com.bugdroidbuilder.paulo.emergencyhelper.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import app.com.bugdroidbuilder.paulo.emergencyhelper.R;
import app.com.bugdroidbuilder.paulo.emergencyhelper.components.ToolbarSupport;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.handler.TelefoneHandler;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.BroadcastResponse;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.GlobalValues;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ConfiguracoesActivity extends AppCompatActivity {

    @Bind(R.id.configuracoes_toolbar)
    Toolbar toolbar;
    @Bind(R.id.settings_height_edt)
    EditText edit;
    @Bind(R.id.configuracoes_salvar)
    Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (GlobalValues.getInstance().isOnline()) {
            setTheme(R.style.AppTheme);
        }else {
            setTheme(R.style.AppThemeOffline);
        }

        setContentView(R.layout.activity_configuracoes);
        ButterKnife.bind(this);
        ToolbarSupport.startToolbarWithArrow(this,toolbar,"Configurações");
        iniciarBtnSalvar();
    }

    public void iniciarBtnSalvar(){
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String test = edit.getText().toString();

                if (TextUtils.isEmpty(test)) {
                    edit.setError("Campo não pode ser vazio");

                }else{
                    TelefoneHandler.telefoneEmergencia = test;
                    getParent().finish();
                }

            }
        });
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
