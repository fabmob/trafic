package com.datex.batch.converter;


import com.datex.batch.model.TrafficMeasureCSV;
import com.datex.batch.model.TrafficPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TrafficCSVConverter extends Converter<TrafficMeasureCSV, TrafficPoint> {
    private static final Logger log = LoggerFactory.getLogger(TrafficCSVConverter.class);

    public TrafficCSVConverter() {
        super(TrafficCSVConverter::convertToPoint);
    }

    public static TrafficPoint convertToPoint(TrafficMeasureCSV in){
        TrafficPoint out = new TrafficPoint();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            Date parsedDate = dateFormat.parse(in.getMeasurementTime());
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            out.setTime(timestamp);
        } catch (ParseException e) { //this generic but you can control another types of exception
            log.error(e.getMessage());
            out.setTime(null);
        }
        out.setCamera(in.getId());
        out.setDirection(in.getDirection());
        out.setLatitude(in.getLatitude());
        out.setLongitude(in.getLongitude());
        out.setRoad(in.getRoad());
        if ("null".equals(in.getAverageVehicleSpeed()))
            in.setAverageVehicleSpeed("0");
        if ("null".equals(in.getTrafficConcentration()))
            in.setTrafficConcentration("0");
        if ("null".equals(in.getVehicleFlowRate()))
            in.setVehicleFlowRate("0");
        try {
            out.setAveragevehiclespeed(Double.parseDouble(in.getAverageVehicleSpeed()));
            out.setTrafficconcentration(Double.parseDouble(in.getTrafficConcentration()));
            out.setVehicleflowrate(Double.parseDouble(in.getVehicleFlowRate()));
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
        }
        return out;
    }
}
