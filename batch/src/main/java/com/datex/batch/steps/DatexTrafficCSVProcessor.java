package com.datex.batch.steps;

import com.datex.batch.converter.TrafficCSVConverter;
import com.datex.batch.model.TrafficMeasureCSV;
import com.datex.batch.model.TrafficPoint;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ParseException;

public class DatexTrafficCSVProcessor implements ItemProcessor<TrafficMeasureCSV, TrafficPoint> {
    @Override
    public TrafficPoint process(TrafficMeasureCSV in) throws ParseException {
        var converter = new TrafficCSVConverter();
        var out = converter.convertFromIn(in);
        if (out.getTime() == null) throw new ParseException("");
        return out;
    }
}
