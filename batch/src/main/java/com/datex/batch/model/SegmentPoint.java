package com.datex.batch.model;

import java.sql.Timestamp;

public class SegmentPoint {
    private Timestamp time;
    private String camera;
    private Double pct_up;
    private String timezone;
    private Double pedestrian;
    private Double bike;
    private Double car;
    private Double lorry;
    private Double pedestrian_lft;
    private Double bike_lft;
    private Double car_lft;
    private Double lorry_lft;
    private Double pedestrian_rgt;
    private Double bike_rgt;
    private Double car_rgt;
    private Double lorry_rgt;
    private Double car_speed_00;
    private Double car_speed_10;
    private Double car_speed_20;
    private Double car_speed_30;
    private Double car_speed_40;
    private Double car_speed_50;
    private Double car_speed_60;
    private Double car_speed_70;

    @Override
    public String toString() {
        return "SegmentPoint{" +
                "time=" + time +
                ", camera='" + camera + '\'' +
                ", pct_up=" + pct_up +
                ", timezone='" + timezone + '\'' +
                ", pedestrian=" + pedestrian +
                ", bike=" + bike +
                ", car=" + car +
                ", lorry=" + lorry +
                ", pedestrian_lft=" + pedestrian_lft +
                ", bike_lft=" + bike_lft +
                ", car_lft=" + car_lft +
                ", lorry_lft=" + lorry_lft +
                ", pedestrian_rgt=" + pedestrian_rgt +
                ", bike_rgt=" + bike_rgt +
                ", car_rgt=" + car_rgt +
                ", lorry_rgt=" + lorry_rgt +
                ", car_speed_00=" + car_speed_00 +
                ", car_speed_10=" + car_speed_10 +
                ", car_speed_20=" + car_speed_20 +
                ", car_speed_30=" + car_speed_30 +
                ", car_speed_40=" + car_speed_40 +
                ", car_speed_50=" + car_speed_50 +
                ", car_speed_60=" + car_speed_60 +
                ", car_speed_70=" + car_speed_70 +
                '}';
    }

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

    public Double getPct_up() {
        return pct_up;
    }

    public void setPct_up(Double pct_up) {
        this.pct_up = pct_up;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Double getPedestrian() {
        return pedestrian;
    }

    public void setPedestrian(Double pedestrian) {
        this.pedestrian = pedestrian;
    }

    public Double getBike() {
        return bike;
    }

    public void setBike(Double bike) {
        this.bike = bike;
    }

    public Double getCar() {
        return car;
    }

    public void setCar(Double car) {
        this.car = car;
    }

    public Double getLorry() {
        return lorry;
    }

    public void setLorry(Double lorry) {
        this.lorry = lorry;
    }

    public Double getPedestrian_lft() {
        return pedestrian_lft;
    }

    public void setPedestrian_lft(Double pedestrian_lft) {
        this.pedestrian_lft = pedestrian_lft;
    }

    public Double getBike_lft() {
        return bike_lft;
    }

    public void setBike_lft(Double bike_lft) {
        this.bike_lft = bike_lft;
    }

    public Double getCar_lft() {
        return car_lft;
    }

    public void setCar_lft(Double car_lft) {
        this.car_lft = car_lft;
    }

    public Double getLorry_lft() {
        return lorry_lft;
    }

    public void setLorry_lft(Double lorry_lft) {
        this.lorry_lft = lorry_lft;
    }

    public Double getPedestrian_rgt() {
        return pedestrian_rgt;
    }

    public void setPedestrian_rgt(Double pedestrian_rgt) {
        this.pedestrian_rgt = pedestrian_rgt;
    }

    public Double getBike_rgt() {
        return bike_rgt;
    }

    public void setBike_rgt(Double bike_rgt) {
        this.bike_rgt = bike_rgt;
    }

    public Double getCar_rgt() {
        return car_rgt;
    }

    public void setCar_rgt(Double car_rgt) {
        this.car_rgt = car_rgt;
    }

    public Double getLorry_rgt() {
        return lorry_rgt;
    }

    public void setLorry_rgt(Double lorry_rgt) {
        this.lorry_rgt = lorry_rgt;
    }

    public Double getCar_speed_00() {
        return car_speed_00;
    }

    public void setCar_speed_00(Double car_speed_00) {
        this.car_speed_00 = car_speed_00;
    }

    public Double getCar_speed_10() {
        return car_speed_10;
    }

    public void setCar_speed_10(Double car_speed_10) {
        this.car_speed_10 = car_speed_10;
    }

    public Double getCar_speed_20() {
        return car_speed_20;
    }

    public void setCar_speed_20(Double car_speed_20) {
        this.car_speed_20 = car_speed_20;
    }

    public Double getCar_speed_30() {
        return car_speed_30;
    }

    public void setCar_speed_30(Double car_speed_30) {
        this.car_speed_30 = car_speed_30;
    }

    public Double getCar_speed_40() {
        return car_speed_40;
    }

    public void setCar_speed_40(Double car_speed_40) {
        this.car_speed_40 = car_speed_40;
    }

    public Double getCar_speed_50() {
        return car_speed_50;
    }

    public void setCar_speed_50(Double car_speed_50) {
        this.car_speed_50 = car_speed_50;
    }

    public Double getCar_speed_60() {
        return car_speed_60;
    }

    public void setCar_speed_60(Double car_speed_60) {
        this.car_speed_60 = car_speed_60;
    }

    public Double getCar_speed_70() {
        return car_speed_70;
    }

    public void setCar_speed_70(Double car_speed_70) {
        this.car_speed_70 = car_speed_70;
    }
}
