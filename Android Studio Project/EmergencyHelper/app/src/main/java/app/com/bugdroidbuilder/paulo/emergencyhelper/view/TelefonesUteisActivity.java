package app.com.bugdroidbuilder.paulo.emergencyhelper.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import app.com.bugdroidbuilder.paulo.emergencyhelper.R;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.PermissionHandler;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.TelefoneHandler;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.TelefoneUtil;
import app.com.bugdroidbuilder.paulo.emergencyhelper.view.components.RecyclerViewListener;
import app.com.bugdroidbuilder.paulo.emergencyhelper.view.components.TelefonesAdapter;
import app.com.bugdroidbuilder.paulo.emergencyhelper.view.components.ToolbarSupport;
import butterknife.Bind;
import butterknife.ButterKnife;

public class TelefonesUteisActivity extends AppCompatActivity {
    @Bind(R.id.telefones_toolbar)
    Toolbar toolbar;
    @Bind(R.id.list_telefones)
    RecyclerView recyclerView;

    private final PermissionHandler permissionHandler = new PermissionHandler();
    private boolean cancelaLigacao = false;
    private List<TelefoneUtil> listaTelefones = new ArrayList<>();
    private TelefonesAdapter mAdapter;
    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telefones);

        ButterKnife.bind(this);

        ToolbarSupport.startToolbarWithArrow(this, toolbar, "Telefones úteis");

        permissionHandler.requestPermissionCall(activity);

        iniciaRecyclerView();

        prepareTelefoneData();


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

    public void cancelarLigacao(View view) {

        TelefoneHandler.cancelarLigacao();
    }


    public void iniciaRecyclerView() {


        mAdapter = new TelefonesAdapter(listaTelefones);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerViewListener.RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerViewListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {

                TelefoneUtil telefoneUtil = listaTelefones.get(position);

                TelefoneHandler.ligarEmergencia(activity,
                        telefoneUtil.getNumero(),
                        R.id.popup_telefones,
                        R.id.fab_cancel_telefones,
                        R.id.text_count_down_telefones);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

}
