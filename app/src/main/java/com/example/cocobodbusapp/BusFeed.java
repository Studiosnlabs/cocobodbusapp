package com.example.cocobodbusapp;

import android.graphics.Bitmap;
import android.widget.TextView;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

public class BusFeed {
    private Bitmap vehicleImage;
    private String vehicleTitle;
    private String morningStatus;
    private String eveningStatus;
    private String driverName;
    private String location;
    private String division;
    private ParseGeoPoint trackPoint;

    public BusFeed(Bitmap vehicleImage, String vehicleTitle, String morningStatus, String eveningStatus, String driverName, String location, String division, ParseGeoPoint trackPoint) {
        this.vehicleImage = vehicleImage;
        this.vehicleTitle = vehicleTitle;
        this.morningStatus = morningStatus;
        this.eveningStatus = eveningStatus;
        this.driverName = driverName;
        this.location = location;
        this.division = division;
        this.trackPoint = trackPoint;
    }

    public Bitmap getVehicleImage() {
        return vehicleImage;
    }

    public void setVehicleImage(Bitmap vehicleImage) {
        this.vehicleImage = vehicleImage;
    }

    public String getVehicleTitle() {
        return vehicleTitle;
    }

    public void setVehicleTitle(String vehicleTitle) {
        this.vehicleTitle = vehicleTitle;
    }

    public String getMorningStatus() {
        return morningStatus;
    }

    public void setMorningStatus(String morningStatus) {
        this.morningStatus = morningStatus;
    }

    public String getEveningStatus() {
        return eveningStatus;
    }

    public void setEveningStatus(String eveningStatus) {
        this.eveningStatus = eveningStatus;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public ParseGeoPoint getTrackPoint() {
        return trackPoint;
    }

    public void setTrackPoint(ParseGeoPoint trackPoint) {
        this.trackPoint = trackPoint;
    }
}
