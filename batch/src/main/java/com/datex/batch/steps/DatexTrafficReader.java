package com.datex.batch.steps;

import com.datex.batch.model.TrafficMeasure;
import com.datex.batch.parser.DatexTrafficParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;


public class DatexTrafficReader implements ItemReader<TrafficMeasure> {
    private static final Logger log = LoggerFactory.getLogger(DatexTrafficReader.class);

    private String  targetUrl;

    URL url = null;
    boolean isInitToDo = true;
    List<TrafficMeasure> listTrafficMeasure = null;

    public DatexTrafficReader(String targetUrl){
        this.targetUrl = targetUrl;
    }

    @Autowired
    private DatexTrafficParser parser;
    private int index = 0;

    private void init() {
        try {
            log.info("Init datex reader :"+targetUrl);
            URL url = new URL(targetUrl);
            try (InputStream in = url.openStream()) {
                listTrafficMeasure = parser.parseXml(in);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TrafficMeasure read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (isInitToDo) {
            init();
            isInitToDo=false;
        }
        if (listTrafficMeasure ==null)
            return null;
        if (index< listTrafficMeasure.size())
            return listTrafficMeasure.get(index++);
        else
            return null;
    }
}
