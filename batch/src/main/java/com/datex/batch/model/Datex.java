package com.datex.batch.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Datex {
    public static final int INTERVAL_IN_MINUTE = 3;

    public static final String URL_METEO = "https://www.cita.lu/info_trafic/datex/weather_dynamic.xml";
    public static final String URL_TELRAAM = "https://telraam-api.net/v0/reports/9000000411";

    public static final String TRAFFIC_A1 = "https://www.cita.lu/info_trafic/datex/trafficstatus_a1";
    public static final String TRAFFIC_A3 = "https://www.cita.lu/info_trafic/datex/trafficstatus_a3";
    public static final String TRAFFIC_A4 = "https://www.cita.lu/info_trafic/datex/trafficstatus_a4";
    public static final String TRAFFIC_A6 = "https://www.cita.lu/info_trafic/datex/trafficstatus_a6";
    public static final String TRAFFIC_A7 = "https://www.cita.lu/info_trafic/datex/trafficstatus_a7";
    public static final String TRAFFIC_A13 = "https://www.cita.lu/info_trafic/datex/trafficstatus_a13";
    public static final String TRAFFIC_B40 = "https://www.cita.lu/info_trafic/datex/trafficstatus_b40";

    public static final String TRAFFIC_EVENT = "http://www.cita.lu/info_trafic/datex/situationrecord";

    public static final Map<String, String> RESOURCE_MAP = createMap();
    public static final String RESOURCE_DEFAULT = "datexData.csv";

    private static final String RESOURCE_A1 = "datexDataA1.csv";
    private static final String RESOURCE_A3 = "datexDataA3.csv";
    private static final String RESOURCE_A4 = "datexDataA4.csv";
    private static final String RESOURCE_A6 = "datexDataA6.csv";
    private static final String RESOURCE_A7 = "datexDataA7.csv";
    private static final String RESOURCE_A13 = "datexDataA13.csv";
    private static final String RESOURCE_B40 = "datexDataB40.csv";

    private static Map<String, String> createMap() {
        Map<String, String> result = new HashMap<String, String>();
        result.put(TRAFFIC_A1, RESOURCE_A1);
        result.put(TRAFFIC_A3, RESOURCE_A3);
        result.put(TRAFFIC_A4, RESOURCE_A4);
        result.put(TRAFFIC_A6, RESOURCE_A6);
        result.put(TRAFFIC_A7, RESOURCE_A7);
        result.put(TRAFFIC_A13, RESOURCE_A13);
        result.put(TRAFFIC_B40, RESOURCE_B40);
        return Collections.unmodifiableMap(result);
    }
    public static final String UNKNOWN = "Unknown";

    public static final String INSERT_TELRAAM = "INSERT INTO telraam(measure_datetime,camera,timezone,pct_up,pedestrian,bike,car,lorry,pedestrian_lft,bike_lft,car_lft,lorry_lft,pedestrian_rgt,bike_rgt,car_rgt,lorry_rgt,car_speed_00,car_speed_10,car_speed_20,car_speed_30,car_speed_40,car_speed_50,car_speed_60,car_speed_70)\n" +
            " VALUES (:time,:camera,:timezone,:pct_up,:pedestrian,:bike,:car,:lorry,:pedestrian_lft,:bike_lft,:car_lft,:lorry_lft,:pedestrian_rgt,:bike_rgt,:car_rgt,:lorry_rgt,:car_speed_00,:car_speed_10,:car_speed_20,:car_speed_30,:car_speed_40,:car_speed_50,:car_speed_60,:car_speed_70)\n" +
            " ON CONFLICT (measure_datetime, camera) DO UPDATE\n" +
            " SET pedestrian = excluded.pedestrian  , pct_up = excluded.pct_up , \n" +
            "bike = excluded.bike  ,\n" +
            "car = excluded.car  ,\n" +
            "lorry = excluded.lorry  ,\n" +
            "pedestrian_lft = excluded.pedestrian_lft  ,\n" +
            "bike_lft = excluded.bike_lft  ,\n" +
            "car_lft = excluded.car_lft  ,\n" +
            "lorry_lft = excluded.lorry_lft  ,\n" +
            "pedestrian_rgt = excluded.pedestrian_rgt  ,\n" +
            "bike_rgt = excluded.bike_rgt  ,\n" +
            "car_rgt = excluded.car_rgt  ,\n" +
            "lorry_rgt = excluded.lorry_rgt  ,\n" +
            "car_speed_00 = excluded.car_speed_00  ,\n" +
            "car_speed_10 = excluded.car_speed_10  ,\n" +
            "car_speed_20 = excluded.car_speed_20  ,\n" +
            "car_speed_30 = excluded.car_speed_30  ,\n" +
            "car_speed_40 = excluded.car_speed_40  ,\n" +
            "car_speed_50 = excluded.car_speed_50  ,\n" +
            "car_speed_60 = excluded.car_speed_60  ,\n" +
            "car_speed_70 = excluded.car_speed_70;";

}
