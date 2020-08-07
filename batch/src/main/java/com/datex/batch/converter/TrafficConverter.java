package com.datex.batch.converter;

import com.datex.batch.model.TrafficMeasure;
import com.datex.batch.model.TrafficPoint;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TrafficConverter extends Converter<TrafficMeasure, TrafficPoint> {
    public TrafficConverter() {
        super(TrafficConverter::convertToPoint);
    }

    public static TrafficPoint convertToPoint(TrafficMeasure in){
        TrafficPoint out = new TrafficPoint();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            Date parsedDate = dateFormat.parse(in.getMeasurementTime());
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            out.setTime(timestamp);
        } catch(Exception e) { //this generic but you can control another types of exception
            e.printStackTrace();
            out.setTime(new Timestamp((new Date()).getTime()));
        }
        out.setCamera(in.getId());
        out.setDirection(in.getDirection());
        out.setLatitude(in.getLatitude());
        out.setLongitude(in.getLongitude());
        out.setRoad(in.getRoad());
        try {
            out.setAveragevehiclespeed(Double.parseDouble(in.getAverageVehicleSpeed()));
            out.setTrafficconcentration(Double.parseDouble(in.getTrafficConcentration()));
            out.setVehicleflowrate(Double.parseDouble(in.getVehicleFlowRate()));
        }catch(NumberFormatException e){
            e.printStackTrace();
        }
        return out;
    }
}
