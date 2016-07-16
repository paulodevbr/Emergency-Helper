package app.com.bugdroidbuilder.paulo.emergencyhelper.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import app.com.bugdroidbuilder.paulo.emergencyhelper.R;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;

public class HospitalDescriptionActivity extends AppCompatActivity {

    private Hospital hospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_description);

        this.hospital = EventBus.getDefault().removeStickyEvent(Hospital.class);

        Map<String,String> linkImagem = this.hospital.getFotos();
        String descricao = this.hospital.getDescricao();
        String nome = this.hospital.getNome();
        String endereco = this.hospital.getEndereco();
        String telefone = this.hospital.getTelefone();
        String tipo = this.hospital.getTipo();

        ImageView image = (ImageView) findViewById(R.id.hospitalImage);
        Picasso.with(this).load(linkImagem.get("Imagem1")).into(image);

        TextView textNome = (TextView) findViewById(R.id.hospitalTitle);
        TextView textDescricao = (TextView) findViewById(R.id.hospitalDescricao);
        TextView textEndereco = (TextView) findViewById(R.id.hospitalEndereco);
        TextView textTelefone = (TextView) findViewById(R.id.hospitalTelefone);
        TextView textTipo = (TextView) findViewById(R.id.hospitalTipo);

        textNome.setText(tipo);
        textDescricao.setText(descricao);
        textEndereco.setText(endereco);
        textTelefone.setText(telefone);
        textTipo.setText(tipo);
        textNome.setText(nome);
    }
}
