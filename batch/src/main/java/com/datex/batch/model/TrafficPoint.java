package com.datex.batch.model;

import java.sql.Timestamp;

public class TrafficPoint {
    private Timestamp time;
    private String camera;
    private String latitude;
    private String longitude;
    private String direction;
    private String road;
    private Double averagevehiclespeed;
    private Double vehicleflowrate;
    private Double trafficconcentration;

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public Double getAveragevehiclespeed() {
        return averagevehiclespeed;
    }

    public void setAveragevehiclespeed(Double averagevehiclespeed) {
        this.averagevehiclespeed = averagevehiclespeed;
    }

    public Double getVehicleflowrate() {
        return vehicleflowrate;
    }

    public void setVehicleflowrate(Double vehicleflowrate) {
        this.vehicleflowrate = vehicleflowrate;
    }

    public Double getTrafficconcentration() {
        return trafficconcentration;
    }

    public void setTrafficconcentration(Double trafficconcentration) {
        this.trafficconcentration = trafficconcentration;
    }
}
