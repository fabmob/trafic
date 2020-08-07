package com.datex.batch.steps;

import com.datex.batch.model.Datex;
import com.datex.batch.model.Report;
import com.datex.batch.model.SegmentMeasure;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TelraamTrafficReader implements ItemReader<SegmentMeasure> {

    private static final Logger log = LoggerFactory.getLogger(TelraamTrafficReader.class);

    boolean isInitToDo = true;
    List<SegmentMeasure> listSegmentMeasure = null;
    private int index = 0;

    private void init() {

        try {
            log.info("Init telraam reader :" + Datex.URL_TELRAAM);
            URL urlstream = new URL(Datex.URL_TELRAAM);
            HttpURLConnection con = (HttpURLConnection) urlstream.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            Instant instant = Instant.now();
            LocalDateTime now = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
            LocalDateTime yesterday = now.minusDays(7);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            String today = now.format(formatter);
            String today1 = yesterday.format(formatter);
            //log.info(today + " " + today1);
            //JSON String need to be constructed for the specific resource.
            //We may construct complex JSON using any third-party JSON libraries such as jackson or org.json
            //String jsonInputString = "{\"name\": \"Upendra\", \"job\": \"Programmer\"}";
            String jsonInputString = "{\"time_start\": \"" + today1 + "Z\",\"time_end\": \"" + today + "Z\",\"level\": \"segments\",\"format\":\"per-hour\"}";
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int code = con.getResponseCode();
            log.info("ResponseCode = " + code);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                log.info(response.toString());

                ObjectMapper objectMapper = new ObjectMapper();

                Report report = objectMapper.readValue(response.toString(), Report.class);

                listSegmentMeasure = report.getReport();
            }
        } catch (MalformedURLException e) {
            log.error("MalformedURLException [" + "url" + "] msg [" + e.getMessage() + "]");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("IOException - msg [" + e.getMessage() + "]");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public SegmentMeasure read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (isInitToDo) {
            init();
            isInitToDo = false;
        }
        if (listSegmentMeasure == null)
            return null;
        if (index < listSegmentMeasure.size())
            return listSegmentMeasure.get(index++);
        else
            return null;
    }
}
