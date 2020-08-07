

-- create database
CREATE DATABASE trafic;
CREATE USER traficuser WITH ENCRYPTED PASSWORD 'traficpwd';
GRANT ALL PRIVILEGES ON DATABASE trafic TO traficuser;

-- connect to trafic
\c trafic
-- Extend the database with TimescaleDB
CREATE EXTENSION IF NOT EXISTS timescaledb CASCADE;

-- create trafic tables
DROP TABLE IF EXISTS camera CASCADE;
CREATE TABLE camera (
    camera    text         NOT NULL,
	latitude NUMERIC NOT   NULL,
    longitude NUMERIC NOT  NULL,
    direction varchar(30) NULL,
    direction_distance NUMERIC  NULL,
    road varchar(10)       NULL
);

DROP TABLE IF EXISTS trafic_time CASCADE;
CREATE TABLE trafic_time (
    measure_datetime TIMESTAMPTZ         NOT NULL,
    camera text                NOT NULL,
    average_vehicle_speed NUMERIC    NULL,
	vehicle_flow_rate      NUMERIC    NULL,
	traffic_concentration NUMERIC   NULL
);

DROP TABLE IF EXISTS meteo CASCADE;
CREATE TABLE meteo(
    measure_datetime        TIMESTAMPTZ         NOT NULL,
    point       text       NOT NULL,
    humidity    NUMERIC    NULL,
    no_precipitation text    NULL,
    millimetres_per_hour_intensity    NUMERIC    NULL,
    road_surface_temperature    NUMERIC    NULL,
    temperature_below_road_surface    NUMERIC    NULL,
    weather_related_road_condition_type text    NULL,
    air_temperature    NUMERIC    NULL,
    dew_point_temperature    NUMERIC    NULL,
    wind_speed    NUMERIC    NULL,
    maximum_wind_speed    NUMERIC    NULL,
    wind_direction_bearing    NUMERIC    NULL,
    status_type text  NULL);

DROP TABLE IF EXISTS telraam CASCADE;
CREATE TABLE telraam(
    measure_datetime        TIMESTAMPTZ         NOT NULL,
    camera       text       NOT NULL,
    timezone text NULL,
    pct_up NUMERIC NULL,
    pedestrian NUMERIC NULL,
    bike NUMERIC NULL,
    car NUMERIC NULL,
    lorry NUMERIC NULL,
    pedestrian_lft NUMERIC NULL,
    bike_lft NUMERIC NULL,
    car_lft NUMERIC NULL,
    lorry_lft NUMERIC NULL,
    pedestrian_rgt NUMERIC NULL,
    bike_rgt NUMERIC NULL,
    car_rgt NUMERIC NULL,
    lorry_rgt NUMERIC NULL,
    car_speed_00 NUMERIC NULL,
    car_speed_10 NUMERIC NULL,
    car_speed_20 NUMERIC NULL,
    car_speed_30 NUMERIC NULL,
    car_speed_40 NUMERIC NULL,
    car_speed_50 NUMERIC NULL,
    car_speed_60 NUMERIC NULL,
    car_speed_70 NUMERIC NULL);

SELECT create_hypertable('trafic_time', 'measure_datetime');
SELECT create_hypertable('meteo', 'measure_datetime');
SELECT create_hypertable('telraam', 'measure_datetime');

CREATE UNIQUE INDEX on trafic_time (measure_datetime, camera);
CREATE UNIQUE INDEX on camera (camera);
CREATE UNIQUE INDEX on meteo (measure_datetime, point);
CREATE UNIQUE INDEX on telraam (measure_datetime, camera);

GRANT ALL PRIVILEGES ON TABLE trafic_time TO traficuser;
GRANT ALL PRIVILEGES ON TABLE camera TO traficuser;
GRANT ALL PRIVILEGES ON TABLE meteo TO traficuser;
GRANT ALL PRIVILEGES ON TABLE telraam TO traficuser;

INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.GT.24538',49.667915,6.3771095,'outboundFromTown',24538,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.GT.26600',49.679363,6.399515,'outboundFromTown',26600,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.GT.15400',49.640390000000004,6.2638326,'outboundFromTown',15400,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.TTHOG.2009',49.578068,6.1509457,'inboundTowardsTown',2009,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.TG.16951',49.643085,6.284836,'inboundTowardsTown',16951,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.TG.880',49.574917,6.1364529999999995,'inboundTowardsTown',880,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.TG.13100',49.638874,6.233522,'inboundTowardsTown',13100,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.GTCET.7367',49.620834,6.1790977,'outboundFromTown',7367,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.TG.15400',49.640495,6.263782,'inboundTowardsTown',15400,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.TG.10810',49.6422,6.2029767,'inboundTowardsTown',10810,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.GT.35064',49.721455,6.4883017999999995,'outboundFromTown',35064,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.GT.3225',49.58677,6.160692,'outboundFromTown',3225,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.GT.13100',49.638794,6.233449,'outboundFromTown',13100,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.TG.3225',49.586815,6.160564,'inboundTowardsTown',3225,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.GT.8099',49.627117,6.1819334,'outboundFromTown',8099,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.GT.16951',49.642994,6.284845,'outboundFromTown',16951,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.TTCEG.7461',49.621646999999996,6.1791635000000005,'inboundTowardsTown',7461,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.TG.13878',49.636826,6.243645,'inboundTowardsTown',13878,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.GT.22026',49.658590000000004,6.345964,'outboundFromTown',22026,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.TG.9669',49.639759999999995,6.187724,'inboundTowardsTown',9669,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.GT.880',49.574825,6.13645,'outboundFromTown',880,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.GT.9669',49.63968,6.187795599999999,'outboundFromTown',9669,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.TG.24538',49.667995,6.377028500000001,'inboundTowardsTown',24538,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.GT.30835',49.694725,6.452195,'outboundFromTown',30835,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.TG.26600',49.679435999999995,6.399426999999999,'inboundTowardsTown',26600,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.TG.6130',49.61032,6.1752294999999995,'inboundTowardsTown',6130,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.GT.4400',49.595387,6.1701794,'outboundFromTown',4400,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.GTHOT.1730',49.576626,6.1477757,'outboundFromTown',1730,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.TG.35079',49.721638,6.488162,'inboundTowardsTown',35079,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.TG.30835',49.694817,6.452156,'inboundTowardsTown',30835,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.GT.6130',49.610275,6.1753540000000005,'outboundFromTown',6130,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.GT.10810',49.64209,6.202998,'outboundFromTown',10810,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A1.TG.4400',49.595436,6.170055400000001,'inboundTowardsTown',4400,'A1');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A3.MV.9438',49.506107,6.114641000000001,'inboundTowardsTown',9438,'A3');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A3.VM.2600',49.564479999999996,6.1288958,'outboundFromTown',2600,'A3');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A3.MV.3490',49.556540000000005,6.129716,'inboundTowardsTown',3490,'A3');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A3.VM.3485',49.55664,6.129586,'outboundFromTown',3485,'A3');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A3.MV.1482',49.574093,6.1245080000000005,'inboundTowardsTown',1482,'A3');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A3.VM.8246',49.516690000000004,6.1163425,'outboundFromTown',8246,'A3');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A3.MV.4691',49.546597,6.123622,'inboundTowardsTown',4691,'A3');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A3.MV.11397',49.48863,6.1172010000000006,'inboundTowardsTown',11397,'A3');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A3.MV.8246',49.516678000000006,6.116487,'inboundTowardsTown',8246,'A3');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A3.VM.100',49.584663,6.1330304,'outboundFromTown',100,'A3');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A3.VM.10437',49.49725,6.116880999999999,'outboundFromTown',10437,'A3');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A3.VM.7280',49.525265000000005,6.1147922999999995,'outboundFromTown',7280,'A3');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A3.VM.4691',49.546659999999996,6.1234839999999995,'outboundFromTown',4691,'A3');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A3.VM.3490',49.556553,6.129571400000001,'outboundFromTown',3490,'A3');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A3.VM.11397',49.488625,6.117055400000001,'outboundFromTown',11397,'A3');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A3.MV.7280',49.525290000000005,6.114932,'inboundTowardsTown',7280,'A3');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A3.MV.2600',49.564503,6.129037,'inboundTowardsTown',2600,'A3');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A3.VM.1482',49.574085,6.1243625,'outboundFromTown',1482,'A3');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A3.MV.10437',49.497257,6.117026,'inboundTowardsTown',10437,'A3');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A3.VM.9438',49.506096,6.1144967,'outboundFromTown',9438,'A3');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A3.VM.5913',49.537395000000004,6.114593,'outboundFromTown',5913,'A3');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A3.MV.100',49.58461,6.1331489999999995,'inboundTowardsTown',100,'A3');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A4.HR.6800',49.557903,6.057836,'outboundFromTown',6800,'A4');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A4.RH.513',49.599823,6.0983047,'inboundTowardsTown',513,'A4');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A4.RH.14060',49.513923999999996,5.9861975,'inboundTowardsTown',14060,'A4');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A4.HR.14060',49.514046,5.9861636,'outboundFromTown',14060,'A4');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A4.HR.10259',49.53645,6.023744000000001,'outboundFromTown',10259,'A4');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A4.RH.6800',49.557827,6.0579233,'inboundTowardsTown',6800,'A4');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A4.HR.513',49.599909999999994,6.0982523,'outboundFromTown',513,'A4');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A4.RH.10259',49.536392,6.0238566,'inboundTowardsTown',10259,'A4');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A4.HR.5200',49.562958,6.0783525,'outboundFromTown',5200,'A4');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A4.RH.13389',49.515736,5.9949746,'inboundTowardsTown',13389,'A4');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A4.RH.1460',49.593857,6.0894523000000005,'inboundTowardsTown',1460,'A4');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A4.HR.1460',49.593895,6.0893180000000005,'outboundFromTown',1460,'A4');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A4.RH.3718',49.573757,6.0883655999999995,'inboundTowardsTown',3718,'A4');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A4.HR.12765',49.519496999999994,6.0014696,'outboundFromTown',12765,'A4');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A4.RH.11614',49.52742,6.011399,'inboundTowardsTown',11614,'A4');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A4.HR.2491',49.584790000000005,6.0872154,'outboundFromTown',2491,'A4');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A4.RH.2546',49.584255,6.087392299999999,'inboundTowardsTown',2546,'A4');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A4.RH.12765',49.519420000000004,6.001591,'inboundTowardsTown',12765,'A4');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A4.HR.3718',49.57376,6.08822,'outboundFromTown',3718,'A4');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A4.RH.5200',49.562874,6.0784144,'inboundTowardsTown',5200,'A4');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.GW.2178',49.581306,6.09567,'outboundFromTown',2178,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.5000',49.602990000000005,6.08026,'inboundTowardsTown',5000,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.16607',49.635666,5.960463,'inboundTowardsTown',16607,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.14318',49.63513,5.991869,'inboundTowardsTown',14318,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.GW.15470',49.635296000000004,5.976136,'outboundFromTown',15470,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.12477',49.638474,6.016314,'inboundTowardsTown',12477,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.GW.12477',49.638565,6.016339,'outboundFromTown',12477,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.7700',49.6261,6.078192,'inboundTowardsTown',7700,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.10430',49.631138,6.042178,'inboundTowardsTown',10430,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.GW.10430',49.63123,6.042215000000001,'outboundFromTown',10430,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.19657',49.637035,5.9184465,'inboundTowardsTown',19657,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.9357',49.630398,6.0569,'inboundTowardsTown',9357,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.18153',49.636078000000005,5.939216,'inboundTowardsTown',18153,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.GW.19871',49.637446999999995,5.9156055,'outboundFromTown',19871,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.1280',49.578520000000005,6.107277,'inboundTowardsTown',1280,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.5968',49.611633000000005,6.082054,'inboundTowardsTown',5968,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.12336',49.638203000000004,6.0182023000000004,'inboundTowardsTown',12336,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.GW.16602',49.63577,5.960587,'outboundFromTown',16602,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.5001',49.602990000000005,6.08026,'inboundTowardsTown',5001,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.GW.14318',49.635222999999996,5.991831299999999,'outboundFromTown',14318,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.GW.5000',49.602978,6.0804043,'outboundFromTown',5000,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.GW.7700',49.626166999999995,6.0782894999999995,'outboundFromTown',7700,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.10413',49.631096,6.042448,'inboundTowardsTown',10413,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.2178',49.581226,6.0955977,'inboundTowardsTown',2178,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.GW.9379',49.63048,6.0566163,'outboundFromTown',9379,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.18762',49.635868,5.930777,'inboundTowardsTown',18762,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.3052',49.586388,6.086616,'inboundTowardsTown',3052,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.8100',49.628166,6.073695,'inboundTowardsTown',8100,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.GW.3780',49.592175,6.0819364,'outboundFromTown',3780,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.16602',49.635674,5.9606010000000005,'inboundTowardsTown',16602,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.15488',49.635242,5.975834,'inboundTowardsTown',15488,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.GW.5968',49.611618,6.082198,'outboundFromTown',5968,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.7747',49.62639,6.0776687,'inboundTowardsTown',7747,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.GW.3052',49.586445,6.0867305,'outboundFromTown',3052,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.GW.18762',49.635963000000004,5.9307847,'outboundFromTown',18762,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.3780',49.592144,6.081799,'inboundTowardsTown',3780,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.9379',49.630390000000006,6.056624,'inboundTowardsTown',9379,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.14317',49.63513,5.991869,'inboundTowardsTown',14317,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.15470',49.635203999999995,5.976104299999999,'inboundTowardsTown',15470,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.19871',49.637356,5.915581700000001,'inboundTowardsTown',19871,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.6586',49.617153,6.083200499999999,'inboundTowardsTown',6586,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.GW.1280',49.578648,6.107305,'outboundFromTown',1280,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A6.WG.11542',49.635259999999995,6.0282397,'inboundTowardsTown',11542,'A6');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A7.NTGRF.6875',49.694035,6.163797,'outboundFromTown',6875,'A7');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A7.FTGRN.8989',49.707256,6.1459,'inboundTowardsTown',8989,'A7');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A7.NF.5909',49.685562,6.1615877,'outboundFromTown',5909,'A7');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A7.NTGRF.8990',49.707367,6.1459208,'outboundFromTown',8990,'A7');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A7.FTSTN.2212',49.657368,6.176981,'inboundTowardsTown',2212,'A7');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A7.NTSTF.3010',49.662186,6.1687574,'outboundFromTown',3010,'A7');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A7.FTGRN.8169',49.704254,6.156033,'inboundTowardsTown',8169,'A7');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A7.NF.16250',49.749996,6.09044,'outboundFromTown',16250,'A7');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A7.FN.3905',49.667809999999996,6.160108999999999,'inboundTowardsTown',3905,'A7');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A7.FTGRN.7423',49.698807,6.1619325,'inboundTowardsTown',7423,'A7');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A7.NF.9570',49.706745,6.1379660000000005,'outboundFromTown',9570,'A7');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A7.NF.800',49.647087,6.1871915,'outboundFromTown',800,'A7');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A7.FN.9570',49.706654,6.137999,'inboundTowardsTown',9570,'A7');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A7.NTGRF.7423',49.69885,6.1620903,'outboundFromTown',7423,'A7');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A7.NF.3905',49.667846999999995,6.160243,'outboundFromTown',3905,'A7');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A7.FTGRN.6875',49.69403,6.1636267,'inboundTowardsTown',6875,'A7');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A7.FN.800',49.647064,6.1870494,'inboundTowardsTown',800,'A7');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A7.NTSTF.2212',49.65744,6.177077,'outboundFromTown',2212,'A7');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A7.FTSTN.3010',49.662116999999995,6.168659,'inboundTowardsTown',3010,'A7');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A7.FN.16250',49.75002,6.0902970000000005,'inboundTowardsTown',16250,'A7');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A7.FN.5909',49.685593,6.1614246,'inboundTowardsTown',5909,'A7');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A7.NTGRF.8169',49.704338,6.156145599999999,'outboundFromTown',8169,'A7');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.PS.35385',49.502388,6.305461,'outboundFromTown',35385,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.PTAES.4962',49.530857,5.9418597,'inboundTowardsTown',4962,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.SP.23974',49.508278000000004,6.1637177,'inboundTowardsTown',23974,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.STMAP.39175',49.480164,6.3433459999999995,'inboundTowardsTown',39175,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.SP.7944',49.518809999999995,5.972471,'outboundFromTown',7944,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.PS.7231',49.524516999999996,5.9680715,'inboundTowardsTown',7231,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.SP.7456',49.522690000000004,5.969571,'outboundFromTown',7456,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.PS.17097',49.50014,6.0746946,'outboundFromTown',17097,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.SP.38620',49.483395,6.337667,'inboundTowardsTown',38620,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.STMAP.40334',49.4777,6.3585725,'inboundTowardsTown',40334,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.PS.40792',49.478374,6.3648233,'outboundFromTown',40792,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.PS.7944',49.518764000000004,5.9723163,'inboundTowardsTown',7944,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.SP.35385',49.502421999999996,6.3055944,'inboundTowardsTown',35385,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.STAEP.4962',49.530945,5.9419135999999995,'outboundFromTown',4962,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.PTMAS.40350',49.47762,6.3588586,'outboundFromTown',40350,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.SP.6630',49.529007,5.963674,'outboundFromTown',6630,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.PTMOS.33066',49.5161,6.2835035,'outboundFromTown',33066,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.PS.38620',49.483334,6.337556,'outboundFromTown',38620,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.PS.23974',49.5082,6.163805,'outboundFromTown',23974,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.PTFRS.26218',49.506783,6.1928434,'outboundFromTown',26218,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.SP.40792',49.478462,6.364778,'inboundTowardsTown',40792,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.STMOP.33066',49.51619,6.283562,'inboundTowardsTown',33066,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.SP.28585',49.511593,6.22445,'inboundTowardsTown',28585,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.PS.3111',49.539646000000005,5.9219555999999995,'inboundTowardsTown',3111,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.PS.6630',49.528927,5.9635935,'inboundTowardsTown',6630,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.SP.3111',49.53969,5.9220843,'outboundFromTown',3111,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.PTMAS.39685',49.478203,6.349682,'outboundFromTown',39685,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.PS.37030',49.493390000000005,6.322593,'outboundFromTown',37030,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.SP.37030',49.493477,6.322649500000001,'inboundTowardsTown',37030,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.STMAP.39674',49.478317,6.3495903,'inboundTowardsTown',39674,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.PS.11615',49.516613,6.0087705,'outboundFromTown',11615,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.STFRP.26477',49.507,6.1964107,'inboundTowardsTown',26477,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.PTMAS.39179',49.480038,6.343385,'outboundFromTown',39179,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.SP.11615',49.516674,6.0088844,'inboundTowardsTown',11615,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.PS.28585',49.511505,6.224487,'outboundFromTown',28585,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('A13.PS.13460',49.511353,6.0317620000000005,'outboundFromTown',13460,'A13');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('B40.HR.18089',49.493904,5.9472966,'outboundFromTown',18089,'B40');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('B40.HR.18260',49.493275,5.9494343,'outboundFromTown',18260,'B40');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('B40.HR.18610',49.491620000000005,5.953512,'outboundFromTown',18610,'B40');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('B40.RH.18260',49.493332,5.949474299999999,'inboundTowardsTown',18260,'B40');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('B40.RH.18089',49.493959999999994,5.9473367,'inboundTowardsTown',18089,'B40');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('B40.HTMIR.17553',49.4975,5.942809,'outboundFromTown',17553,'B40');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('B40.RTMIH.17553',49.49751,5.942953599999999,'inboundTowardsTown',17553,'B40');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('B40.RH.18610',49.491695,5.953599,'inboundTowardsTown',18610,'B40');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('B40.HTMIR.17853',49.495059999999995,5.944498,'outboundFromTown',17853,'B40');
INSERT INTO camera(camera,latitude,longitude,direction,direction_distance,road) VALUES ('B40.RTMIH.17853',49.495125,5.944604,'inboundTowardsTown',17853,'B40');


-- create batch tables
CREATE TABLE BATCH_JOB_INSTANCE  (
	JOB_INSTANCE_ID BIGINT  NOT NULL PRIMARY KEY ,
	VERSION BIGINT ,
	JOB_NAME VARCHAR(100) NOT NULL,
	JOB_KEY VARCHAR(32) NOT NULL,
	constraint JOB_INST_UN unique (JOB_NAME, JOB_KEY)
) ;
GRANT ALL PRIVILEGES ON TABLE BATCH_JOB_INSTANCE TO traficuser;
CREATE TABLE BATCH_JOB_EXECUTION  (
	JOB_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
	VERSION BIGINT  ,
	JOB_INSTANCE_ID BIGINT NOT NULL,
	CREATE_TIME TIMESTAMP NOT NULL,
	START_TIME TIMESTAMP DEFAULT NULL ,
	END_TIME TIMESTAMP DEFAULT NULL ,
	STATUS VARCHAR(10) ,
	EXIT_CODE VARCHAR(2500) ,
	EXIT_MESSAGE VARCHAR(2500) ,
	LAST_UPDATED TIMESTAMP,
	JOB_CONFIGURATION_LOCATION VARCHAR(2500) NULL,
	constraint JOB_INST_EXEC_FK foreign key (JOB_INSTANCE_ID)
	references BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
) ;
GRANT ALL PRIVILEGES ON TABLE BATCH_JOB_EXECUTION TO traficuser;


CREATE TABLE BATCH_JOB_EXECUTION_PARAMS  (
	JOB_EXECUTION_ID BIGINT NOT NULL ,
	TYPE_CD VARCHAR(6) NOT NULL ,
	KEY_NAME VARCHAR(100) NOT NULL ,
	STRING_VAL VARCHAR(250) ,
	DATE_VAL TIMESTAMP DEFAULT NULL ,
	LONG_VAL BIGINT ,
	DOUBLE_VAL DOUBLE PRECISION ,
	IDENTIFYING CHAR(1) NOT NULL ,
	constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;
GRANT ALL PRIVILEGES ON TABLE BATCH_JOB_EXECUTION_PARAMS TO traficuser;

CREATE TABLE BATCH_STEP_EXECUTION  (
	STEP_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
	VERSION BIGINT NOT NULL,
	STEP_NAME VARCHAR(100) NOT NULL,
	JOB_EXECUTION_ID BIGINT NOT NULL,
	START_TIME TIMESTAMP NOT NULL ,
	END_TIME TIMESTAMP DEFAULT NULL ,
	STATUS VARCHAR(10) ,
	COMMIT_COUNT BIGINT ,
	READ_COUNT BIGINT ,
	FILTER_COUNT BIGINT ,
	WRITE_COUNT BIGINT ,
	READ_SKIP_COUNT BIGINT ,
	WRITE_SKIP_COUNT BIGINT ,
	PROCESS_SKIP_COUNT BIGINT ,
	ROLLBACK_COUNT BIGINT ,
	EXIT_CODE VARCHAR(2500) ,
	EXIT_MESSAGE VARCHAR(2500) ,
	LAST_UPDATED TIMESTAMP,
	constraint JOB_EXEC_STEP_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;
GRANT ALL PRIVILEGES ON TABLE BATCH_STEP_EXECUTION TO traficuser;

CREATE TABLE BATCH_STEP_EXECUTION_CONTEXT  (
	STEP_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
	SHORT_CONTEXT VARCHAR(2500) NOT NULL,
	SERIALIZED_CONTEXT TEXT ,
	constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
	references BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
) ;
GRANT ALL PRIVILEGES ON TABLE BATCH_STEP_EXECUTION_CONTEXT TO traficuser;

CREATE TABLE BATCH_JOB_EXECUTION_CONTEXT  (
	JOB_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
	SHORT_CONTEXT VARCHAR(2500) NOT NULL,
	SERIALIZED_CONTEXT TEXT ,
	constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;
GRANT ALL PRIVILEGES ON TABLE BATCH_JOB_EXECUTION_CONTEXT TO traficuser;

CREATE SEQUENCE BATCH_STEP_EXECUTION_SEQ MAXVALUE 9223372036854775807 NO CYCLE;
CREATE SEQUENCE BATCH_JOB_EXECUTION_SEQ MAXVALUE 9223372036854775807 NO CYCLE;
CREATE SEQUENCE BATCH_JOB_SEQ MAXVALUE 9223372036854775807 NO CYCLE;


GRANT ALL PRIVILEGES ON SEQUENCE BATCH_STEP_EXECUTION_SEQ TO traficuser;
GRANT ALL PRIVILEGES ON SEQUENCE BATCH_JOB_EXECUTION_SEQ TO traficuser;
GRANT ALL PRIVILEGES ON SEQUENCE BATCH_JOB_SEQ TO traficuser;
