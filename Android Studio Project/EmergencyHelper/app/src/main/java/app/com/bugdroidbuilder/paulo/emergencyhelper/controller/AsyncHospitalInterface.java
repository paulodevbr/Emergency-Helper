package app.com.bugdroidbuilder.paulo.emergencyhelper.controller;

import java.util.List;
import java.util.Set;

import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;

/**
 * Created by pedro on 26/06/16.
 */
public interface AsyncHospitalInterface {
    void processFinishHospital(Set<Hospital> output);
}
