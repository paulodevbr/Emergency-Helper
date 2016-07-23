package app.com.bugdroidbuilder.paulo.emergencyhelper.model;

/**
 * Created by pedro on 23/07/16.
 */
public class BroadcastResponse {

    private boolean valorBoolean;
    private int flag;

    public static final int FLAG_INTERNET = 0;
    public static final int FLAG_GPS = 1;

    public BroadcastResponse(boolean valorBoolean, int flag) {
        this.valorBoolean = valorBoolean;
        this.flag = flag;
    }

    public boolean getValor() {
        return valorBoolean;
    }

    public int getFlag() {
        return flag;
    }
}
