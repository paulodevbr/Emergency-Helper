package app.com.bugdroidbuilder.paulo.emergencyhelper.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import app.com.bugdroidbuilder.paulo.emergencyhelper.R;

public class HospitalDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_description);

        Intent intent = this.getIntent();

        String linkImagem = intent.getStringExtra("imagem");
        String descricao = intent.getStringExtra("descricao");
        String nome = intent.getStringExtra("nome");
        String endereco = intent.getStringExtra("endereco");
        String telefone = intent.getStringExtra("telefone");
        String tipo = intent.getStringExtra("tipo");

        ImageView image = (ImageView) findViewById(R.id.hospitalImage);
        Picasso.with(this).load(linkImagem).into(image);

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
