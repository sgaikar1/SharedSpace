package com.emagicindia.realeastate.model;

/**
 * Created by dell on 8/16/2016.
 */
public class AdressParameters {

    private String city;
    private String subLocality;


    public AdressParameters() {

    }

    public AdressParameters(String city, String subLocality) {
        this.city = city;
        this.subLocality = subLocality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSubLocality() {
        return subLocality;
    }

    public void setSubLocality(String subLocality) {
        this.subLocality = subLocality;
    }
}
