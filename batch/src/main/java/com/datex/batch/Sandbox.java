package com.datex.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sandbox {
    private static final Logger log = LoggerFactory.getLogger(Sandbox.class);

//    public static void main(String[] args) {
//        try {
//            URL urlstream = new URL("https://telraam-api.net/v0/reports/9000000411");
//            HttpURLConnection con = (HttpURLConnection) urlstream.openConnection();
//            con.setRequestMethod("POST");
//
//            con.setRequestProperty("Content-Type", "application/json");
//            con.setRequestProperty("Accept", "application/json");
//
//            con.setDoOutput(true);
//
//            Instant instant = Instant.now();
//            LocalDateTime now = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
//            LocalDateTime yesterday = now.minusDays(1);
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//            String today = now.format(formatter);
//            String today1 = yesterday.format(formatter);
//            log.info(today + " " + today1);
//            //JSON String need to be constructed for the specific resource.
//            //We may construct complex JSON using any third-party JSON libraries such as jackson or org.json
//            //String jsonInputString = "{\"name\": \"Upendra\", \"job\": \"Programmer\"}";
//            String jsonInputString = "{\"time_start\": \""+today1+"Z\",\"time_end\": \""+today+"Z\",\"level\": \"segments\",\"format\":\"per-hour\"}";
//            try (OutputStream os = con.getOutputStream()) {
//                byte[] input = jsonInputString.getBytes("utf-8");
//                os.write(input, 0, input.length);
//            }
//
//            int code = con.getResponseCode();
//            log.info("ResponseCode = "+code);
//
//            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
//                StringBuilder response = new StringBuilder();
//                String responseLine = null;
//                while ((responseLine = br.readLine()) != null) {
//                    response.append(responseLine.trim());
//                }
//                log.info(response.toString());
//
//                ObjectMapper objectMapper = new ObjectMapper();
//
//                Report report = objectMapper.readValue(response.toString(), Report.class);
//
//                report.getReport().stream().forEach(m->log.info(m.toString()));
//            }
//        } catch (MalformedURLException e) {
//            log.error("MalformedURLException [" + "url" + "] msg [" + e.getMessage() + "]");
//            e.printStackTrace();
//        } catch (IOException e) {
//            log.error("IOException - msg [" + e.getMessage() + "]");
//            e.printStackTrace();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args){
    String toParse = "2020-07-23T04:00:00.000Z";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            Date parsedDate = dateFormat.parse(toParse);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            log.info(timestamp.toString());
        } catch(Exception e) { //this generic but you can control another types of exception
            e.printStackTrace();
        }

    }

//    public static void main(String[] args) {
//        List<TrafficMeasure> out = new ArrayList<>();
//
//        try {
//            URL urlstream = new URL(Datex.TRAFFIC_A3);
//            DatexTrafficParser parser = new DatexTrafficParser();
//            out.addAll(parser.parseXml(urlstream.openStream()));
//        } catch (MalformedURLException e) {
//            log.error("MalformedURLException [" + "url" + "] msg [" + e.getMessage() + "]");
//            e.printStackTrace();
//        } catch (IOException e) {
//            log.error("IOException - msg [" + e.getMessage() + "]");
//            e.printStackTrace();
//        }
//        out.forEach(m->log.info(m.toString()));
//    }

//    public static void main(String[] args) {
//        List<MeteoMeasure> out = new ArrayList<>();
//
//        try {
//            URL urlstream = new URL(Datex.URL_METEO);
//            DatexMeteoParser parser = new DatexMeteoParser();
//            out.addAll(parser.parseXml(urlstream.openStream()));
//        } catch (MalformedURLException e) {
//            log.error("MalformedURLException [" + "url" + "] msg [" + e.getMessage() + "]");
//            e.printStackTrace();
//        } catch (IOException e) {
//            log.error("IOException - msg [" + e.getMessage() + "]");
//            e.printStackTrace();
//        }
//        out.forEach(m->log.info(m.toString()));
//    }
}
