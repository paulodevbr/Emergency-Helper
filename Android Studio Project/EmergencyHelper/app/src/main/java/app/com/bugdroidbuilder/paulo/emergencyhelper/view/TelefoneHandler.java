package app.com.bugdroidbuilder.paulo.emergencyhelper.view;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.com.bugdroidbuilder.paulo.emergencyhelper.R;

/**
 * Created by paulo on 13/07/16.
 */
public class TelefoneHandler {

    private static boolean cancelaLigacao = false;
    private static boolean efetuarLigacao = false;

    public static boolean ligar(Activity activity, Intent intent){

        if (intent.getAction().equals(Intent.ACTION_CALL)) {
            final RelativeLayout layout = (RelativeLayout) activity.findViewById(R.id.count_down_ligacao);
            layout.setVisibility(View.VISIBLE);

            final TextView text = (TextView) activity.findViewById(R.id.text_count_down);

            new CountDownTimer(3000, 100) {

                public void onTick(long millisUntilFinished) {
                    String segundos = Long.toString((millisUntilFinished / 1000)+1);
                    text.setText(segundos);

                    if(cancelaLigacao){
                        layout.setVisibility(View.GONE);
                        cancelaLigacao = false;
                        efetuarLigacao = false;
                        cancel();
                    }
                }

                public void onFinish() {
                    layout.setVisibility(View.GONE);
                    efetuarLigacao = true;
                }

            }.start();

    }
        return efetuarLigacao;

}
    public static void cancelarLigacao(){
        cancelaLigacao = true;
    }

}
