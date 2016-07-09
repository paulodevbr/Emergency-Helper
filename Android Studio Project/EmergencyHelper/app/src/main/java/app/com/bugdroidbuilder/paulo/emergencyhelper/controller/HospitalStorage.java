package app.com.bugdroidbuilder.paulo.emergencyhelper.controller;

import java.util.Set;

import app.com.bugdroidbuilder.paulo.emergencyhelper.model.Hospital;

/**
 * Created by pedro on 09/07/16.
 */
public class HospitalStorage {
    private static HospitalStorage ourInstance = new HospitalStorage();
    private Set<Hospital> hospitalSet;

    public static HospitalStorage getInstance() {
        return ourInstance;
    }

    private HospitalStorage() {
    }

    public Set<Hospital> getStorage(){
        return hospitalSet;
    }

    public void setStorage(Set<Hospital> hospitalSet){
        this.hospitalSet = hospitalSet;
    }
}
