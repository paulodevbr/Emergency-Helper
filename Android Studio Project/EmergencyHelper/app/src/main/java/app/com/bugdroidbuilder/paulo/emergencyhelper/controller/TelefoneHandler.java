package app.com.bugdroidbuilder.paulo.emergencyhelper.controller;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by paulo on 13/07/16.
 */
public class TelefoneHandler {
    private static int TEMPO_CONTAGEM = 5000;
    private static boolean cancelaLigacao = false;

    public static void ligarEmergencia(final Activity activity, String numeroEmergencia, int popupId,
                                       int fabId, int msgId) {

        String uri = "tel:" + numeroEmergencia.trim();
        final Intent intent;

        if (app.com.bugdroidbuilder.paulo.emergencyhelper.controller.PermissionHandler.permissionCall) {
            intent = new Intent(Intent.ACTION_CALL);

        } else {
            intent = new Intent(Intent.ACTION_DIAL);
        }

        intent.setData(Uri.parse(uri));

        if (intent.getAction().equals(Intent.ACTION_CALL)) {
            final RelativeLayout popup = (RelativeLayout) activity.findViewById(popupId);
            popup.setVisibility(View.VISIBLE);

            final FloatingActionButton fab = (FloatingActionButton) activity.findViewById(fabId);
            fab.show();

            final TextView text = (TextView) activity.findViewById(msgId);
            text.setVisibility(View.VISIBLE);

            new CountDownTimer(TEMPO_CONTAGEM, 100) {

                public void onTick(long millisUntilFinished) {
                    String segundos = Long.toString((millisUntilFinished / 1000) + 1);
                    text.setText(segundos);

                    if (cancelaLigacao) {
                        fab.hide();
                        text.setVisibility(View.GONE);
                        popup.setVisibility(View.GONE);
                        cancelaLigacao = false;
                        cancel();
                    }
                }

                public void onFinish() {
                    fab.hide();
                    text.setVisibility(View.GONE);
                    popup.setVisibility(View.GONE);
                    activity.startActivity(intent);
                }

            }.start();

        } else {
            activity.startActivity(intent);
        }

    }

    public static void discar(Activity activity, String numero){

        String uri = "tel:" + numero.trim();
        final Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        activity.startActivity(intent);
    }


    public static void cancelarLigacao() {
        cancelaLigacao = true;
    }

}
