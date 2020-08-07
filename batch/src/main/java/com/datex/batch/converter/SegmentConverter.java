package com.datex.batch.converter;

import com.datex.batch.model.SegmentMeasure;
import com.datex.batch.model.SegmentPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SegmentConverter extends Converter<SegmentMeasure, SegmentPoint> {
    private static final Logger log = LoggerFactory.getLogger(SegmentConverter.class);
    public SegmentConverter() {
        super(SegmentConverter::convertToPoint);
    }

    private static SegmentPoint convertToPoint(SegmentMeasure segmentMeasure) {
        SegmentPoint point = new SegmentPoint();
        point.setCamera(segmentMeasure.getSegment_id());
        point.setTimezone(segmentMeasure.getTimezone());
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            Date parsedDate = dateFormat.parse(segmentMeasure.getDate());
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime()+3600*2*1000);
            point.setTime(timestamp);
        } catch (Exception e) { //this generic but you can control another types of exception
            e.printStackTrace();
            point.setTime(new Timestamp((new Date()).getTime()));
        }

        //log.info(point.getTime().toString());
        point.setBike(segmentMeasure.getBike());
        point.setBike_lft(segmentMeasure.getCar_lft());
        point.setBike_rgt(segmentMeasure.getBike_rgt());
        point.setCar(segmentMeasure.getCar());
        point.setCar_lft(segmentMeasure.getCar_lft());
        point.setCar_rgt(segmentMeasure.getCar_rgt());
        point.setPct_up(segmentMeasure.getPct_up());
        point.setPedestrian(segmentMeasure.getPedestrian());
        point.setPedestrian_lft(segmentMeasure.getPedestrian_lft());
        point.setPedestrian_rgt(segmentMeasure.getPedestrian_rgt());
        point.setLorry(segmentMeasure.getLorry());
        point.setLorry_lft(segmentMeasure.getLorry_lft());
        point.setLorry_rgt(segmentMeasure.getLorry_rgt());

        var speed_bucket = segmentMeasure.getCar_speed_bucket();
        for (int i = 0; i < speed_bucket.size(); i++) {
            int ii = speed_bucket.get(i);
            switch (ii){
                case 0:
                    point.setCar_speed_00(segmentMeasure.getCar_speed_histogram().get(i));
                    break;
                case 1:
                    point.setCar_speed_10(segmentMeasure.getCar_speed_histogram().get(i));
                    break;
                case 2:
                    point.setCar_speed_20(segmentMeasure.getCar_speed_histogram().get(i));
                    break;
                case 3:
                    point.setCar_speed_30(segmentMeasure.getCar_speed_histogram().get(i));
                    break;
                case 4:
                    point.setCar_speed_40(segmentMeasure.getCar_speed_histogram().get(i));
                    break;
                case 5:
                    point.setCar_speed_50(segmentMeasure.getCar_speed_histogram().get(i));
                    break;
                case 6:
                    point.setCar_speed_60(segmentMeasure.getCar_speed_histogram().get(i));
                    break;
                case 7:
                    point.setCar_speed_70(segmentMeasure.getCar_speed_histogram().get(i));
                    break;
            }
        }



        return point;
    }
}
