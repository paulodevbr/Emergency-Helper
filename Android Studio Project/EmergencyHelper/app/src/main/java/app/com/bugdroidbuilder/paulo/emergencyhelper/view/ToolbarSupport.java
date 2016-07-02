package app.com.bugdroidbuilder.paulo.emergencyhelper.view;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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
    public static void startToolbarWithArrow(AppCompatActivity activity, Toolbar toolbar, String title){
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle(title);

        //Setup the button to quit this activity
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Set the color of the arrow
        final Drawable upArrow = activity.getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(activity.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        activity.getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    /**
     * start a simple toolbar with  with actionBar support
     * @param activity activity opening the toolbar
     * @param toolbar toolbar object
     * @param title title on the toolbar
     */
    public static void startToolbar(AppCompatActivity activity, Toolbar toolbar, String title){
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle(title);

    }
}
