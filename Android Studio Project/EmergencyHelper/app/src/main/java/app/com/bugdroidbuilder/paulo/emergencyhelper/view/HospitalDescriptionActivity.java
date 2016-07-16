package app.com.bugdroidbuilder.paulo.emergencyhelper.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import app.com.bugdroidbuilder.paulo.emergencyhelper.R;
import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;
import butterknife.Bind;
import butterknife.ButterKnife;

public class HospitalDescriptionActivity extends AppCompatActivity {

        public static final String EXTRA_DISCO = "disco";

        @Bind(R.id.fabFavorito)
        FloatingActionButton mFabFavorito;
        @Bind(R.id.imgCapa)
        ImageView mImgCapa;
        @Bind(R.id.txtTitulo)
        TextView mTxtTitulo;
        @Bind(R.id.txtAno)
        TextView mTxtAno;
        @Bind(R.id.txtGravadora)
        TextView mTxtGravadora;
        @Bind(R.id.txtFormacao)
        TextView mTxtFormacao;
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


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_hospital_description);

            ButterKnife.bind(this);
            Intent intent = getIntent();
            Hospital hospital = new Hospital(intent.getStringExtra("nome"),
                    intent.getStringExtra("descricao"),intent.getStringExtra("endereco"));

            preencherCampos(hospital);

            configurarBarraDeTitulo(hospital.getNome());

            carregarFoto(hospital);

            configurarAnimacaoEntrada();


            configurarFab(hospital);

        }

        private void carregarFoto(Hospital hospital) {
            Intent intent = getIntent();
            String linkImagem = intent.getStringExtra("imagem");
            if (mPicassoTarget == null){
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
            Picasso.with(this)
                    .load(linkImagem)
                    .into(mPicassoTarget);
        }

        private void preencherCampos(Hospital hospital) {
            mTxtTitulo.setText(hospital.getNome());
            mTxtAno.setText(String.valueOf(hospital.getDescricao()));
            mTxtGravadora.setText(hospital.getEndereco());

        }

        private void configurarBarraDeTitulo(String titulo) {
            setSupportActionBar(mToolbar);
            if (mAppBar != null) {
                if (mAppBar.getLayoutParams() instanceof CoordinatorLayout.LayoutParams ) {
                    CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) mAppBar.getLayoutParams();
                    lp.height = getResources().getDisplayMetrics().widthPixels;
                }
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (mCollapsingToolbarLayout != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                mCollapsingToolbarLayout.setTitle(titulo);
            } else {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }

        private void configurarAnimacaoEntrada() {
            ViewCompat.setTransitionName(mImgCapa, "capa");
            ViewCompat.setTransitionName(mTxtTitulo, "titulo");
            ViewCompat.setTransitionName(mTxtAno, "ano");
            ActivityCompat.postponeEnterTransition(this);
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

        private void definirCores(Bitmap bitmap){
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    int vibrantColor = palette.getVibrantColor(Color.BLACK);
                    int darkVibrantColor = palette.getDarkVibrantColor(Color.BLACK);
                    int darkMutedColor = palette.getDarkMutedColor(Color.BLACK);
                    int lightMutedColor = palette.getLightMutedColor(Color.WHITE);

                    mTxtTitulo.setTextColor(vibrantColor);
                    if (mAppBar != null) {
                        mAppBar.setBackgroundColor(vibrantColor);
                    } else {
                        mToolbar.setBackgroundColor(Color.TRANSPARENT);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setNavigationBarColor(darkMutedColor);
                    }
                    if (mCollapsingToolbarLayout != null) {
                        mCollapsingToolbarLayout.setContentScrimColor(darkVibrantColor);
                    }
                    mCoordinator.setBackgroundColor(lightMutedColor);
                    iniciarAnimacaoDeEntrada(mCoordinator);
                }
            });
        }

        private void configurarFab(final Hospital hospital) {
//            boolean favorito = mDiscoDb.favorito(disco);
//            mFabFavorito.setImageDrawable(getFabIcone(favorito));
//            mFabFavorito.setBackgroundTintList(getFabBackground(favorito));
            mFabFavorito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

//        private Drawable getFabIcone(boolean favorito){
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//                return ResourcesCompat.getDrawable(
//                        getResources(),
//                        favorito ? R.drawable.ic_cancel_anim : R.drawable.ic_check_anim,
//                        getTheme());
//            } else {
//                return getResources().getDrawable(
//                        favorito ? R.drawable.ic_cancel : R.drawable.ic_check);
//            }
//        }
//        private ColorStateList getFabBackground(boolean favorito) {
//            return getResources().getColorStateList(favorito ?
//                    R.color.bg_fab_cancel : R.color.bg_fab_favorito);
//        }
        @Override
        public void onBackPressed() {
            mFabFavorito.animate().scaleX(0).scaleY(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    HospitalDescriptionActivity.super.onBackPressed();
                }
            }).start();
        }
//
//        private void animar(boolean favorito){
//            mFabFavorito.setBackgroundTintList(getFabBackground(favorito));
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//                AnimatedVectorDrawable avd = (AnimatedVectorDrawable) getFabIcone(!favorito);
//                mFabFavorito.setImageDrawable(avd);
//                avd.start();
//            } else {
//                mFabFavorito.setImageDrawable(getFabIcone(favorito));
//            }
//        }



}
