package com.datex.batch.garbage;

import com.datex.batch.model.TrafficMeasure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class MeasurementProcessor implements ItemProcessor<TrafficMeasure, TrafficMeasure> {
    private static final Logger log = LoggerFactory.getLogger(MeasurementProcessor.class);
    @Override
    public TrafficMeasure process(TrafficMeasure in) throws Exception {
        log.info(in.print(";"));
        return in;
    }
}
