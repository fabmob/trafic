package com.datex.batch.steps;

import com.datex.batch.model.Datex;
import com.datex.batch.model.MeteoMeasure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatexMeteoProcessor implements ItemProcessor<MeteoMeasure, MeteoMeasure> {
    private static final Logger log = LoggerFactory.getLogger(DatexMeteoProcessor.class);


    @Override
    public MeteoMeasure process(MeteoMeasure meteoMeasure) throws Exception {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            Date parsedDate = dateFormat.parse(meteoMeasure.getMeasurementTime());
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            meteoMeasure.setTime(timestamp);
        } catch (Exception e) { //this generic but you can control another types of exception
            e.printStackTrace();
            meteoMeasure.setTime(new Timestamp((new Date()).getTime()));
        }
        if (meteoMeasure.getNoPrecipitation() == null)
            meteoMeasure.setNoPrecipitation(Datex.UNKNOWN);

        if (meteoMeasure.getStatusType() == null)
            meteoMeasure.setStatusType(Datex.UNKNOWN);

        if (meteoMeasure.getWeatherRelatedRoadConditionType() == null)
            meteoMeasure.setWeatherRelatedRoadConditionType(Datex.UNKNOWN);
        return meteoMeasure;
    }
}
