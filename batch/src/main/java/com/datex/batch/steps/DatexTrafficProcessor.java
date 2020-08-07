package com.datex.batch.steps;

import com.datex.batch.converter.TrafficConverter;
import com.datex.batch.model.TrafficMeasure;
import com.datex.batch.model.TrafficPoint;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

public class DatexTrafficProcessor implements ItemProcessor<TrafficMeasure, TrafficPoint> {
    private JobExecution jobExecution;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        jobExecution = stepExecution.getJobExecution();
    }

    @Override
    public TrafficPoint process(TrafficMeasure in) throws Exception {
        var converter = new TrafficConverter();
        return converter.convertFromIn(in);
    }
}
