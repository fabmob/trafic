package com.datex.batch.steps;

import com.datex.batch.model.Datex;
import com.datex.batch.model.MeteoMeasure;
import com.datex.batch.parser.DatexMeteoParser;
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

public class DatexMeteoReader implements ItemReader<MeteoMeasure> {
    private static final Logger log = LoggerFactory.getLogger(DatexMeteoReader.class);

    boolean isInitToDo = true;
    List<MeteoMeasure> listMeteoMeasure = null;

    @Autowired
    private DatexMeteoParser parser;
    private int index = 0;

    private void init() {
        try {
            log.info("Init meteo datex reader :" + Datex.URL_METEO);
            URL url = new URL(Datex.URL_METEO);
            try (InputStream in = url.openStream()) {
                listMeteoMeasure = parser.parseXml(in);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MeteoMeasure read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (isInitToDo) {
            init();
            isInitToDo = false;
        }
        if (listMeteoMeasure == null)
            return null;
        if (index < listMeteoMeasure.size())
            return listMeteoMeasure.get(index++);
        else
            return null;
    }
}
