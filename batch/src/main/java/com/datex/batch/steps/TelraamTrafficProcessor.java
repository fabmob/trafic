package com.datex.batch.steps;

import com.datex.batch.converter.SegmentConverter;
import com.datex.batch.model.SegmentMeasure;
import com.datex.batch.model.SegmentPoint;
import org.springframework.batch.item.ItemProcessor;

public class TelraamTrafficProcessor implements ItemProcessor<SegmentMeasure, SegmentPoint> {
    @Override
    public SegmentPoint process(SegmentMeasure segmentMeasure) throws Exception {
        var converter = new SegmentConverter();
        return converter.convertFromIn(segmentMeasure);
    }
}
