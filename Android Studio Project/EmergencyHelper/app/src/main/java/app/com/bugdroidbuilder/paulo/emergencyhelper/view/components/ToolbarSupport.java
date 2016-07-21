package app.com.bugdroidbuilder.paulo.emergencyhelper.view.components;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import app.com.bugdroidbuilder.paulo.emergencyhelper.R;


/**
 * Created by paulo on 28/06/16.
 */
public class ToolbarSupport {

    /**
     * start a toolbar with an arrow button back with actionBar support
     * @param activity activity opening the toolbar
     * @param toolbar toolbar object
     * @param title title on the toolbar
     */
    public static void startToolbarWithArrow(final AppCompatActivity activity, final Toolbar toolbar, String title){

        toolbar.setTitle(title);
        activity.setSupportActionBar(toolbar);

        //Setup the button to quit this activity
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    /**
     * start a simple toolbar with  with actionBar support
     * @param activity activity opening the toolbar
     * @param toolbar toolbar object
     */
    public static void startToolbar(AppCompatActivity activity, final Toolbar toolbar, String title){

        toolbar.setTitle(title);
        activity.setSupportActionBar(toolbar);

    }
}
