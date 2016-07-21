package app.com.bugdroidbuilder.paulo.emergencyhelper.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.greenrobot.eventbus.EventBus;

import app.com.bugdroidbuilder.paulo.emergencyhelper.R;
import app.com.bugdroidbuilder.paulo.emergencyhelper.controller.TelefoneHandler;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;
import app.com.bugdroidbuilder.paulo.emergencyhelper.view.components.ToolbarSupport;
import butterknife.Bind;
import butterknife.ButterKnife;

public class HospitalDescriptionActivity extends AppCompatActivity {


    @Bind(R.id.fabFavorito)
    FloatingActionButton mFabFavorito;
    @Bind(R.id.imgCapa)
    ImageView mImgCapa;
    @Bind(R.id.txt_nome_hospital)
    TextView txtNomeHospital;
    @Bind(R.id.txt_telefone_hospital)
    TextView txtTelefoneHospital;
    @Bind(R.id.txt_especialidade)
    TextView txtEspecialidade;
    @Bind(R.id.txt_descricao)
    TextView txtDescricaoHospital;
    @Bind(R.id.txtMusicas)
    TextView mTxtMusicas;

    @Nullable
    @Bind(R.id.coordinator)
    CoordinatorLayout mCoordinator;
    @Nullable
    @Bind(R.id.appBar)
    AppBarLayout mAppBar;
    @Nullable
    @Bind(R.id.collapseToolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    Target mPicassoTarget;
    private Hospital hospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_description);

        ButterKnife.bind(this);
        this.hospital = EventBus.getDefault().removeStickyEvent(Hospital.class);

        preencherCampos(hospital);
        configurarBarraDeTitulo(hospital.getNome());
        carregarFoto(hospital);
        configurarFab(hospital);
    }

    private void carregarFoto(Hospital hospital) {

        String linkImagem = hospital.getFotos().get("imagem1");
        if (mPicassoTarget == null) {
            mPicassoTarget = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    mImgCapa.setImageBitmap(bitmap);
                    iniciarAnimacaoDeEntrada(mCoordinator);
                    definirCores(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    iniciarAnimacaoDeEntrada(mCoordinator);
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            };
        }

        Picasso.with(this).load(linkImagem).into(mPicassoTarget);
    }

    private void preencherCampos(Hospital hospital) {
        txtNomeHospital.setText(hospital.getNome());

        txtTelefoneHospital.setText(hospital.getTelefone());
        txtTelefoneHospital.setPaintFlags(txtTelefoneHospital.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtTelefoneHospital.setText(String.valueOf(hospital.getTelefone()));

        txtEspecialidade.setText(hospital.getEndereco());

    }

    private void configurarBarraDeTitulo(String titulo) {
        ToolbarSupport.startToolbarWithArrow(this, mToolbar, titulo);
        if (mAppBar != null) {
            if (mAppBar.getLayoutParams() instanceof CoordinatorLayout.LayoutParams) {
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) mAppBar.getLayoutParams();
                lp.height = getResources().getDisplayMetrics().widthPixels;
            }
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (mCollapsingToolbarLayout != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mCollapsingToolbarLayout.setTitle(titulo);
            mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorTitleText));
            mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorTitleText));
        } else {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }


    private void iniciarAnimacaoDeEntrada(final View sharedElement) {
        sharedElement.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                        ActivityCompat.startPostponedEnterTransition(HospitalDescriptionActivity.this);
                        return true;
                    }
                });
    }

    private void definirCores(Bitmap bitmap) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int vibrantColor = palette.getVibrantColor(Color.BLACK);
                int darkVibrantColor = palette.getDarkVibrantColor(Color.RED);
                int darkMutedColor = palette.getDarkMutedColor(Color.BLACK);
                int lightMutedColor = palette.getLightMutedColor(Color.WHITE);

                txtNomeHospital.setTextColor(vibrantColor);
                if (mAppBar != null) {
                    mAppBar.setBackgroundColor(vibrantColor);
                } else {
                    mToolbar.setBackgroundColor(Color.TRANSPARENT);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setNavigationBarColor(darkMutedColor);
                }
                if (mCollapsingToolbarLayout != null) {
                    mCollapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimary));
                }
                mCoordinator.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                iniciarAnimacaoDeEntrada(mCoordinator);
            }
        });
    }

    private void configurarFab(final Hospital hospital) {

        mFabFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tagEndereco = getResources().getString(R.string.endereco_maps);
                String linkMaps = getResources().getString(R.string.link_maps);
                String pacoteMaps = getResources().getString(R.string.pacote_maps);
                // Create a Uri from an intent string. Use the result to create an Intent.
                StringBuilder endereco = new StringBuilder().append(tagEndereco)
                        .append(hospital.getLatitude())
                        .append(",")
                        .append(hospital.getLongitude());

                StringBuilder maps = new StringBuilder().append(linkMaps).append(endereco.toString());
                Uri gmmIntentUri = Uri.parse(maps.toString());


                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                mapIntent.setPackage(pacoteMaps);

                startActivity(mapIntent);
            }
        });
    }

    public void ligarHospital(View view){

        TelefoneHandler.discar(this, this.hospital.getTelefone());
    }

}
