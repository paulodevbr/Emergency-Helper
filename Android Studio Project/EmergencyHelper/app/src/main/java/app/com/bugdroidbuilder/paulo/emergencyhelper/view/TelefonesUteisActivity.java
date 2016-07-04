package app.com.bugdroidbuilder.paulo.emergencyhelper.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.com.bugdroidbuilder.paulo.emergencyhelper.R;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.PermissionHandler;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.TelefoneUtil;

public class TelefonesUteisActivity extends AppCompatActivity {

    private List<TelefoneUtil> listaTelefones = new ArrayList<>();
    private RecyclerView recyclerView;
    private TelefonesAdapter mAdapter;
    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telefones);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.telefones_toolbar);
        ToolbarSupport.startToolbarWithArrow(this, toolbar,"Telefones úteis");

        PermissionHandler permissionHandler = new PermissionHandler();
        permissionHandler.requestPermissionCall(activity);

        recyclerView = (RecyclerView) findViewById(R.id.list_telefones);

        mAdapter = new TelefonesAdapter(listaTelefones);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareTelefoneData();

        recyclerView.addOnItemTouchListener(new RecyclerViewListener.RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerViewListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                TelefoneUtil telefoneUtil = listaTelefones.get(position);
                String uri = "tel:" + telefoneUtil.getNumero().trim() ;
                final Intent intent;

                if(PermissionHandler.permissionCall){
                    intent = new Intent(Intent.ACTION_CALL);

                }else{
                    intent = new Intent(Intent.ACTION_DIAL);
                }

                intent.setData(Uri.parse(uri));

                new CountDownTimer(3200, 1000) {

                    public void onTick(long millisUntilFinished) {

                        Toast.makeText(getApplicationContext(), "Ligando em 3 segundos", Toast.LENGTH_SHORT).show();
                    }

                    public void onFinish() {
                        startActivity(intent);
                    }

                }.start();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void prepareTelefoneData() {

        listaTelefones.add(new TelefoneUtil("SAMU", getResources().
                getString(R.string.telefone_samu)));
        listaTelefones.add(new TelefoneUtil("Corpo de Bombeiros", getResources().
                getString(R.string.telefone_corpo_de_bombeiros)));
        listaTelefones.add(new TelefoneUtil("Polícia Militar", getResources().
                getString(R.string.telefone_policia_militar)));
        listaTelefones.add(new TelefoneUtil("Polícia Rodoviária Federal", getResources().
                getString(R.string.telefone_policia_rodoviaria_federal)));

        mAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // If the user click to go back, the app goes to MainActivity
        finish();
        return super.onOptionsItemSelected(item);
    }

}