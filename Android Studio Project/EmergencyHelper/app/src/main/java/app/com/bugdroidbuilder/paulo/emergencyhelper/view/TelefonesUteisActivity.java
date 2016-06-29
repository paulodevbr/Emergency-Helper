package app.com.bugdroidbuilder.paulo.emergencyhelper.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import app.com.bugdroidbuilder.paulo.emergencyhelper.R;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.TelefoneUtil;

public class TelefonesUteisActivity extends AppCompatActivity {

    private List<TelefoneUtil> listaTelefones = new ArrayList<>();
    private RecyclerView recyclerView;
    private TelefonesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telefones);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.telefones_toolbar);
        ToolbarSupport.startToolbarWithArrow(this, toolbar,"Telefones úteis");

        recyclerView = (RecyclerView) findViewById(R.id.list_telefones);

        mAdapter = new TelefonesAdapter(listaTelefones);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareTelefoneData();

    }

    private void prepareTelefoneData() {

        listaTelefones.add(new TelefoneUtil("SAMU", "192"));
        listaTelefones.add(new TelefoneUtil("Policia Militar", "190"));
        mAdapter.notifyDataSetChanged();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // If the user click to go back, the app goes to MainActivity
        finish();
        return super.onOptionsItemSelected(item);
    }

}
