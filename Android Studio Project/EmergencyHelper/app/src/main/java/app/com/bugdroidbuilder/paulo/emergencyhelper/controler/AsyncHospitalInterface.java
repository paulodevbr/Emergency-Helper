package app.com.bugdroidbuilder.paulo.emergencyhelper.controler;

import java.util.List;

import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;

/**
 * Created by pedro on 26/06/16.
 */
public interface AsyncHospitalInterface {
    void processFinish(List<Hospital> output);
}
