package com.example.trackinghub_basic.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Vehical_List_Model {

    @SerializedName("lati")
    private String Latitude;

    @SerializedName("longi")
    public String Longitude;

    @SerializedName("address")
    public String Address;

    @SerializedName("Date")
    public String Date;

    @SerializedName("speed")
    public String Speed;

    @SerializedName("ignition")
    public String Ignition;

    @SerializedName("current_event")
    public String Current_event;

    @SerializedName("assets_name")
    public String Vehical_number;

    @SerializedName("km_reading")
    public String KM_Reading;

    @SerializedName("device_id")
    public String Device_id;

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getSpeed() {
        return Speed;
    }

    public void setSpeed(String speed) {
        this.Speed = speed;
    }

    public String getIgnition() {
        return Ignition;
    }

    public void setIgnition(String ignition) {
        Ignition = ignition;
    }

    public String getCurrent_event() {
        return Current_event;
    }

    public void setCurrent_event(String current_event) {
        Current_event = current_event;
    }

    public String getVehical_number() {
        return Vehical_number;
    }

    public void setVehical_number(String vehical_number) {
        Vehical_number = vehical_number;
    }

    public String getKM_reading() {
        return KM_Reading;
    }

    public void setKM_reading(String KM_reading) {
        this.KM_Reading = KM_reading;
    }

    public String getDevice_id() {
        return Device_id;
    }

    public void setDevice_id(String device_id) {
        Device_id = device_id;
    }


}


