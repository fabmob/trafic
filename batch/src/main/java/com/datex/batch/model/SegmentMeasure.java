package com.datex.batch.model;

import java.util.List;

public class SegmentMeasure {
    private String segment_id;
    private String date;
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
    private List<Double> car_speed_histogram;
    private List<Integer> car_speed_bucket;

    @Override
    public String toString() {
        return "Segment{" +
                "segment_id='" + segment_id + '\'' +
                ", date='" + date + '\'' +
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
                ", car_speed_histogram=" + car_speed_histogram +
                ", car_speed_bucket=" + car_speed_bucket +
                '}';
    }

    public String getSegment_id() {
        return segment_id;
    }

    public void setSegment_id(String segment_id) {
        this.segment_id = segment_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public List<Double> getCar_speed_histogram() {
        return car_speed_histogram;
    }

    public void setCar_speed_histogram(List<Double> car_speed_histogram) {
        this.car_speed_histogram = car_speed_histogram;
    }

    public List<Integer> getCar_speed_bucket() {
        return car_speed_bucket;
    }

    public void setCar_speed_bucket(List<Integer> car_speed_bucket) {
        this.car_speed_bucket = car_speed_bucket;
    }
}
